package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.entity.UserProfile;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.config.AppSecurityProperties;
import com.scrummanager.dto.request.ActivateAccountRequest;
import com.scrummanager.dto.request.LoginRequest;
import com.scrummanager.dto.request.RegisterRequest;
import com.scrummanager.dto.response.AuthResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.security.JwtTokenProvider;
import com.scrummanager.security.PasswordPolicyValidator;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordPolicyValidator passwordPolicyValidator;
    private final AppSecurityProperties securityProperties;
    private final CacheInvalidationService cacheInvalidationService;
    private final TokenStateService tokenStateService;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public UserResponse register(RegisterRequest req) {
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

        userRepository.save(user);
        cacheInvalidationService.evictUserCaches(user);
        domainEventPublisher.publish(
                "auth.user.registered",
                user.getId(),
                "user",
                user.getId(),
                Map.of("username", user.getUsername(), "email", user.getEmail()));
        return toUserResponse(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = findByUsernameOrEmail(req.usernameOrEmail()).orElse(null);
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
            cacheInvalidationService.evictUserCaches(persistedUser);

            String token = tokenProvider.generateToken(principal);
            domainEventPublisher.publish(
                    "auth.user.logged-in",
                    persistedUser.getId(),
                    "user",
                    persistedUser.getId(),
                    Map.of("username", persistedUser.getUsername()));
            return new AuthResponse(token, persistedUser.getId(), persistedUser.getUsername());
        } catch (BadCredentialsException ex) {
            handleFailedLogin(user, req.usernameOrEmail());
            throw new BadCredentialsException("Username/email or password is incorrect");
        }
    }

    @Transactional
    public void activateAccount(ActivateAccountRequest req) {
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
                req.newPassword(),
                user.getUsername(),
                user.getEmail(),
                user.getProfile() != null ? user.getProfile().getName() : null);

        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        user.setStatus(AccountStatus.ACTIVE);
        user.setTokenVersion(Optional.ofNullable(user.getTokenVersion()).orElse(0) + 1);
        resetLoginControls(user);
        LocalDateTime now = LocalDateTime.now();
        user.setLastPasswordChangeAt(now);
        user.setPasswordExpiresAt(now.plusDays(securityProperties.getPassword().getMaxAgeDays()));
        userRepository.save(user);
        cacheInvalidationService.evictUserCaches(user);
        domainEventPublisher.publish(
                "security.password.rotated",
                user.getId(),
                "user",
                user.getId(),
                Map.of("username", user.getUsername(), "tokenVersion", user.getTokenVersion()));
    }

    public void logout(String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (token == null) {
            throw new BadCredentialsException("Authorization bearer token is required");
        }

        try {
            JwtTokenProvider.TokenMetadata tokenMetadata = tokenProvider.getTokenMetadata(token);
            tokenStateService.blacklist(tokenMetadata.tokenId(), tokenMetadata.expiresAt());

            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("username", tokenMetadata.username());
            payload.put("tokenVersion", tokenMetadata.tokenVersion());
            payload.put("tokenId", tokenMetadata.tokenId());
            domainEventPublisher.publish(
                    "auth.user.logged-out",
                    tokenMetadata.userId(),
                    "user",
                    tokenMetadata.userId(),
                    payload);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("Token is invalid");
        }
    }

    static UserResponse toUserResponse(User u) {
        return new UserResponse(
                u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }

    private Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    private void ensureAccountIsNotLocked(User user) {
        if (user != null
                && user.getAccountLockedUntil() != null
                && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new LockedException("Account temporarily locked after repeated failed logins");
        }
    }

    private void handleFailedLogin(User user, String usernameOrEmail) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("identifier", usernameOrEmail);

        if (user == null) {
            domainEventPublisher.publish("security.login.failed", null, "user", null, payload);
            return;
        }

        int failedAttempts = Optional.ofNullable(user.getFailedLoginAttempts()).orElse(0) + 1;
        user.setFailedLoginAttempts(failedAttempts);

        if (failedAttempts >= securityProperties.getLockout().getMaxAttempts()) {
            user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(securityProperties.getLockout().getDurationMinutes()));
            payload.put("lockedUntil", user.getAccountLockedUntil());
        }

        userRepository.save(user);
        cacheInvalidationService.evictUserCaches(user);
        payload.put("failedAttempts", failedAttempts);
        payload.put("username", user.getUsername());
        domainEventPublisher.publish("security.login.failed", user.getId(), "user", user.getId(), payload);
    }

    private void resetLoginControls(User user) {
        user.setFailedLoginAttempts(0);
        user.setAccountLockedUntil(null);
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }

        String normalized = authorizationHeader.trim();
        if (normalized.toLowerCase(Locale.ROOT).startsWith("bearer ")) {
            return normalized.substring(7).trim();
        }
        return null;
    }
}
