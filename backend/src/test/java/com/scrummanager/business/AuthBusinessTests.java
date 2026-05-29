package com.scrummanager.business;

import com.scrummanager.config.AppSecurityProperties;
import com.scrummanager.business.contract.LoginResult;
import com.scrummanager.domain.dto.request.ActivateAccountRequest;
import com.scrummanager.domain.dto.request.LoginRequest;
import com.scrummanager.domain.model.User;
import com.scrummanager.domain.model.UserProfile;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.security.PasswordPolicyValidator;
import com.scrummanager.service.contract.TokenStateContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthBusinessTests {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenStateContract tokenStateService;

    private AuthBusiness authBusiness;

    @BeforeEach
    void setUp() {
        AppSecurityProperties properties = new AppSecurityProperties();
        PasswordPolicyValidator passwordPolicyValidator = new PasswordPolicyValidator(properties);
        authBusiness = new AuthBusiness(
                userRepository,
                passwordEncoder,
                authenticationManager,
                passwordPolicyValidator,
                properties,
                tokenStateService);
    }

    @Test
    void loginLocksAccountAfterRepeatedFailures() {
        User user = baseUser();
        user.setFailedLoginAttempts(4);

        when(userRepository.findByUsernameOrEmail("roger", "roger")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authBusiness.login(new LoginRequest("roger", "wrong")));

        assertEquals(5, user.getFailedLoginAttempts());
        assertNotNull(user.getAccountLockedUntil());
        verify(userRepository).save(user);
    }

    @Test
    void loginResetsFailureCountersOnSuccess() {
        User user = baseUser();
        user.setFailedLoginAttempts(3);
        user.setAccountLockedUntil(LocalDateTime.now().minusMinutes(1));

        AuthenticatedUserPrincipal principal = AuthenticatedUserPrincipal.fromUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(userRepository.findByUsernameOrEmail("roger", "roger")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        LoginResult result = authBusiness.login(new LoginRequest("roger", "UltraSecure#2026"));

        assertEquals(user.getUsername(), result.user().getUsername());
        assertEquals(0, result.user().getFailedLoginAttempts());
        assertNull(result.user().getAccountLockedUntil());
        assertNotNull(result.user().getLastLoginAt());
        verify(userRepository).save(user);
    }

    @Test
    void activateAccountRotatesTokenVersionAndResetsLockout() {
        User user = baseUser();
        user.setTokenVersion(2);
        user.setFailedLoginAttempts(5);
        user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(10));

        when(userRepository.findByUsername("roger")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("my answer", user.getSecurityAnswerHash())).thenReturn(true);
        when(passwordEncoder.encode("UltraSecure#2027")).thenReturn("encoded-password");

        authBusiness.activateAccount(new ActivateAccountRequest("roger", "Question?", "my answer", "UltraSecure#2027"));

        assertEquals(3, user.getTokenVersion());
        assertEquals(0, user.getFailedLoginAttempts());
        assertNull(user.getAccountLockedUntil());
        assertEquals("encoded-password", user.getPasswordHash());
        assertNotNull(user.getLastPasswordChangeAt());
        assertNotNull(user.getPasswordExpiresAt());
        verify(userRepository).save(user);
    }

    private User baseUser() {
        return User.builder()
                .id(7L)
                .username("roger")
                .email("roger@example.com")
                .passwordHash("encoded")
                .securityQuestion("Question?")
                .securityAnswerHash("encoded-answer")
                .registrationDate(LocalDate.now())
                .status(AccountStatus.ACTIVE)
                .tokenVersion(0)
                .failedLoginAttempts(0)
                .profile(UserProfile.builder()
                        .name("Roger Salvadeo")
                        .availability(Availability.AVAILABLE)
                        .build())
                .build();
    }
}
