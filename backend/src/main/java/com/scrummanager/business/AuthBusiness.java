package com.scrummanager.business;

import com.scrummanager.config.AppSecurityProperties;
import com.scrummanager.business.contract.AuthBusinessContract;
import com.scrummanager.business.contract.LoginResult;
import com.scrummanager.domain.dto.request.ActivateAccountRequest;
import com.scrummanager.domain.dto.request.LoginRequest;
import com.scrummanager.domain.dto.request.RegisterRequest;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.domain.model.User;
import com.scrummanager.domain.model.UserProfile;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.security.PasswordPolicyValidator;
import com.scrummanager.service.contract.TokenStateContract;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthBusiness implements AuthBusinessContract {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final PasswordPolicyValidator passwordPolicyValidator;
    private final AppSecurityProperties securityProperties;
    private final TokenStateContract tokenStateService;

    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already taken: " + req.username());
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered: " + req.email());
        }
        passwordPolicyValidator.validate(req.password(), req.username(), req.email(), req.profileName());

        LocalDateTime now = LocalDateTime.now();
        UserProfile profile = UserProfile.builder()
                .name(req.profileName())
                .availability(Availability.AVAILABLE)
                .build();

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .passwordHash(passwordEncoder.encode(req.password()))
                .securityQuestion(req.securityQuestion())
                .securityAnswerHash(passwordEncoder.encode(req.securityAnswer().toLowerCase(Locale.ROOT)))
                .registrationDate(LocalDate.now())
                .status(AccountStatus.ACTIVE)
                .tokenVersion(0)
                .failedLoginAttempts(0)
                .lastPasswordChangeAt(now)
                .passwordExpiresAt(now.plusDays(securityProperties.getPassword().getMaxAgeDays()))
                .profile(profile)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public LoginResult login(LoginRequest req) {
        User user = userRepository.findByUsernameOrEmail(req.usernameOrEmail(), req.usernameOrEmail()).orElse(null);
        ensureAccountIsNotLocked(user);

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.usernameOrEmail(), req.password()));
            AuthenticatedUserPrincipal principal = (AuthenticatedUserPrincipal) auth.getPrincipal();
            User persistedUser = userRepository.findById(principal.getId())
                    .orElseThrow(() -> new BadCredentialsException("User could not be loaded"));

            resetLoginControls(persistedUser);
            persistedUser.setLastLoginAt(LocalDateTime.now());
            userRepository.save(persistedUser);

            return new LoginResult(persistedUser, principal);
        } catch (BadCredentialsException ex) {
            handleFailedLogin(user, req.usernameOrEmail());
            throw new BadCredentialsException("Username/email or password is incorrect");
        }
    }

    @Transactional
    public User activateAccount(ActivateAccountRequest req) {
        User user = userRepository.findByUsername(req.usernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(req.usernameOrEmail())
                        .orElseThrow(() -> new IllegalArgumentException("User not found")));

        if (!user.getSecurityQuestion().trim().equalsIgnoreCase(req.securityQuestion().trim())) {
            throw new BadCredentialsException("Security question is incorrect");
        }
        if (!passwordEncoder.matches(req.securityAnswer().toLowerCase(Locale.ROOT), user.getSecurityAnswerHash())) {
            throw new BadCredentialsException("Security answer is incorrect");
        }

        passwordPolicyValidator.validate(
                req.newPassword(), user.getUsername(), user.getEmail(),
                user.getProfile() != null ? user.getProfile().getName() : null);

        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        user.setStatus(AccountStatus.ACTIVE);
        user.setTokenVersion(Optional.ofNullable(user.getTokenVersion()).orElse(0) + 1);
        resetLoginControls(user);

        LocalDateTime now = LocalDateTime.now();
        user.setLastPasswordChangeAt(now);
        user.setPasswordExpiresAt(now.plusDays(securityProperties.getPassword().getMaxAgeDays()));
        return userRepository.save(user);
    }

    public void blacklistToken(String tokenId, Instant expiresAt) {
        tokenStateService.blacklist(tokenId, expiresAt);
    }

    private void ensureAccountIsNotLocked(User user) {
        if (user != null
                && user.getAccountLockedUntil() != null
                && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new org.springframework.security.authentication.LockedException(
                    "Account temporarily locked after repeated failed logins");
        }
    }

    private void handleFailedLogin(User user, String usernameOrEmail) {
        if (user == null) {
            return;
        }
        int failedAttempts = Optional.ofNullable(user.getFailedLoginAttempts()).orElse(0) + 1;
        user.setFailedLoginAttempts(failedAttempts);
        if (failedAttempts >= securityProperties.getLockout().getMaxAttempts()) {
            user.setAccountLockedUntil(
                    LocalDateTime.now().plusMinutes(securityProperties.getLockout().getDurationMinutes()));
        }
        userRepository.save(user);
    }

    private void resetLoginControls(User user) {
        user.setFailedLoginAttempts(0);
        user.setAccountLockedUntil(null);
    }

}
