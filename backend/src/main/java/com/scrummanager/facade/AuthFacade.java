package com.scrummanager.facade;

import com.scrummanager.business.contract.AuthBusinessContract;
import com.scrummanager.business.contract.LoginResult;
import com.scrummanager.domain.dto.request.ActivateAccountRequest;
import com.scrummanager.domain.dto.request.LoginRequest;
import com.scrummanager.domain.dto.request.RegisterRequest;
import com.scrummanager.domain.dto.response.AuthResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.model.User;
import com.scrummanager.facade.contract.AuthFacadeContract;
import com.scrummanager.security.JwtTokenProvider;
import com.scrummanager.service.contract.CacheInvalidationContract;
import com.scrummanager.service.contract.DomainEventPublisherContract;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthFacade implements AuthFacadeContract {

    private final AuthBusinessContract authBusiness;
    private final JwtTokenProvider tokenProvider;
    private final CacheInvalidationContract cacheInvalidationService;
    private final DomainEventPublisherContract domainEventPublisher;

    public UserResponse register(RegisterRequest req) {
        User user = authBusiness.register(req);
        cacheInvalidationService.evictUserCaches(user);
        domainEventPublisher.publish(
                "auth.user.registered", user.getId(), "user", user.getId(),
                Map.of("username", user.getUsername(), "email", user.getEmail()));
        return toUserResponse(user);
    }

    public AuthResponse login(LoginRequest req) {
        LoginResult result = authBusiness.login(req);
        User user = result.user();
        cacheInvalidationService.evictUserCaches(user);
        String token = tokenProvider.generateToken(result.principal());
        domainEventPublisher.publish(
                "auth.user.logged-in", user.getId(), "user", user.getId(),
                Map.of("username", user.getUsername()));
        return new AuthResponse(token, user.getId(), user.getUsername());
    }

    public void activateAccount(ActivateAccountRequest req) {
        User user = authBusiness.activateAccount(req);
        cacheInvalidationService.evictUserCaches(user);
        domainEventPublisher.publish(
                "security.password.rotated", user.getId(), "user", user.getId(),
                Map.of("username", user.getUsername(), "tokenVersion", user.getTokenVersion()));
    }

    public void logout(String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (token == null) {
            throw new BadCredentialsException("Authorization bearer token is required");
        }
        try {
            JwtTokenProvider.TokenMetadata meta = tokenProvider.getTokenMetadata(token);
            authBusiness.blacklistToken(meta.tokenId(), meta.expiresAt());
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("username", meta.username());
            payload.put("tokenVersion", meta.tokenVersion());
            payload.put("tokenId", meta.tokenId());
            domainEventPublisher.publish(
                    "auth.user.logged-out", meta.userId(), "user", meta.userId(), payload);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("Token is invalid");
        }
    }

    private static UserResponse toUserResponse(User u) {
        return new UserResponse(
                u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        String normalized = authorizationHeader.trim();
        if (normalized.toLowerCase(Locale.ROOT).startsWith("bearer ")) {
            return normalized.substring(7).trim();
        }
        return null;
    }
}
