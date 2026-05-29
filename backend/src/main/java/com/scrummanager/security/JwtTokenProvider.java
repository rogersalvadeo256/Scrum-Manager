package com.scrummanager.security;

import com.scrummanager.service.contract.TokenStateContract;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final TokenStateContract tokenStateService;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(AuthenticatedUserPrincipal principal) {
        String tokenId = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(principal.getUsername())
                .id(tokenId)
                .claim("uid", principal.getId())
                .claim("ver", principal.getTokenVersion())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public TokenMetadata getTokenMetadata(String token) {
        Claims claims = parseClaims(token);
        return new TokenMetadata(
                claims.getSubject(),
                claims.get("uid", Long.class),
                claims.get("ver", Integer.class),
                claims.getId(),
                claims.getExpiration().toInstant());
    }

    public boolean validateToken(String token, AuthenticatedUserPrincipal principal) {
        try {
            TokenMetadata tokenMetadata = getTokenMetadata(token);
            return Objects.equals(tokenMetadata.username(), principal.getUsername())
                    && Objects.equals(tokenMetadata.userId(), principal.getId())
                    && Objects.equals(tokenMetadata.tokenVersion(), principal.getTokenVersion())
                    && !tokenStateService.isBlacklisted(tokenMetadata.tokenId());
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Invalid JWT token: {}", ex.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public record TokenMetadata(String username, Long userId, Integer tokenVersion, String tokenId, Instant expiresAt) {
    }
}
