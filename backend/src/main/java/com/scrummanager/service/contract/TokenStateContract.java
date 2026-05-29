package com.scrummanager.service.contract;

import java.time.Instant;

public interface TokenStateContract {
    void blacklist(String tokenId, Instant expiresAt);
    boolean isBlacklisted(String tokenId);
    void purgeExpiredTokens();
}
