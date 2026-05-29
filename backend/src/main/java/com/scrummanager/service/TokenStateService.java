package com.scrummanager.service;

import com.scrummanager.service.contract.TokenStateContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenStateService implements TokenStateContract {

    private static final String BLACKLIST_PREFIX = "scrum-manager:blacklist:";

    private final ObjectProvider<StringRedisTemplate> redisTemplateProvider;
    private final Map<String, Instant> localBlacklist = new ConcurrentHashMap<>();

    public void blacklist(String tokenId, Instant expiresAt) {
        if (tokenId == null || expiresAt == null) {
            return;
        }

        Duration ttl = Duration.between(Instant.now(), expiresAt);
        if (ttl.isNegative() || ttl.isZero()) {
            return;
        }

        StringRedisTemplate redisTemplate = redisTemplateProvider.getIfAvailable();
        if (redisTemplate != null) {
            try {
                redisTemplate.opsForValue().set(BLACKLIST_PREFIX + tokenId, "1", ttl);
                return;
            } catch (RuntimeException ex) {
                log.warn("Redis unavailable while blacklisting token {}: {}", tokenId, ex.getMessage());
            }
        }

        localBlacklist.put(tokenId, expiresAt);
    }

    public boolean isBlacklisted(String tokenId) {
        if (tokenId == null) {
            return false;
        }

        StringRedisTemplate redisTemplate = redisTemplateProvider.getIfAvailable();
        if (redisTemplate != null) {
            try {
                return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + tokenId));
            } catch (RuntimeException ex) {
                log.warn("Redis unavailable while validating blacklist {}: {}", tokenId, ex.getMessage());
            }
        }

        purgeExpiredTokens();
        Instant expiresAt = localBlacklist.get(tokenId);
        return expiresAt != null && expiresAt.isAfter(Instant.now());
    }

    public void purgeExpiredTokens() {
        Instant now = Instant.now();
        Iterator<Map.Entry<String, Instant>> iterator = localBlacklist.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getValue().isAfter(now)) {
                iterator.remove();
            }
        }
    }
}
