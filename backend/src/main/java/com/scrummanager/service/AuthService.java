package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.entity.UserProfile;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.dto.request.ActivateAccountRequest;
import com.scrummanager.dto.request.LoginRequest;
import com.scrummanager.dto.request.RegisterRequest;
import com.scrummanager.dto.response.AuthResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already taken: " + req.username());
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered: " + req.email());
        }

        UserProfile profile = UserProfile.builder()
                .name(req.profileName())
                .availability(Availability.AVAILABLE)
                .build();

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .passwordHash(passwordEncoder.encode(req.password()))
                .securityQuestion(req.securityQuestion())
                .securityAnswerHash(passwordEncoder.encode(req.securityAnswer().toLowerCase()))
                .registrationDate(LocalDate.now())
                .status(AccountStatus.ACTIVE)
                .profile(profile)
                .build();

        userRepository.save(user);
        return toUserResponse(user);
    }

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.usernameOrEmail(), req.password()));

        String token = tokenProvider.generateToken(auth);
        User user = userRepository.findByUsername(auth.getName())
                .orElseGet(() -> userRepository.findByEmail(auth.getName()).orElseThrow());
        return new AuthResponse(token, user.getId(), user.getUsername());
    }

    @Transactional
    public void activateAccount(ActivateAccountRequest req) {
        User user = userRepository.findByUsername(req.usernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(req.usernameOrEmail())
                        .orElseThrow(() -> new IllegalArgumentException("User not found")));

        if (!passwordEncoder.matches(req.securityAnswer().toLowerCase(), user.getSecurityAnswerHash())) {
            throw new BadCredentialsException("Security answer is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        user.setStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
    }

    static UserResponse toUserResponse(User u) {
        return new UserResponse(
                u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }
}
