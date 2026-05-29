package com.scrummanager.security;

import com.scrummanager.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;

/**
 * Authenticates requests to {@code /external/**} (except {@code /external/health}) via
 * the {@code X-Api-Key} request header. The raw key is compared against the
 * SHA-256 hash stored in the {@code api_keys} table.
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Api-Key";
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private final ApiKeyRepository apiKeyRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !MATCHER.match("/external/**", path)
                || MATCHER.match("/external/health", path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String rawKey = request.getHeader(HEADER_NAME);
        if (rawKey == null || rawKey.isBlank()) {
            writeUnauthorized(response, "X-Api-Key header is required");
            return;
        }

        String keyHash = sha256Hex(rawKey.trim());
        var apiKeyOpt = apiKeyRepository.findByKeyHashAndActiveTrue(keyHash);

        if (apiKeyOpt.isEmpty()) {
            writeUnauthorized(response, "Invalid or inactive API key");
            return;
        }

        var apiKey = apiKeyOpt.get();
        if (apiKey.getExpiresAt() != null && apiKey.getExpiresAt().isBefore(LocalDateTime.now())) {
            writeUnauthorized(response, "API key has expired");
            return;
        }

        // Set a minimal authentication so Spring Security considers the request authenticated
        var auth = new UsernamePasswordAuthenticationToken(
                apiKey.getOwner().getId(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private static void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(
                ("{\"status\":401,\"title\":\"Unauthorized\",\"detail\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8));
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
