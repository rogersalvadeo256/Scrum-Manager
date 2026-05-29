package com.scrummanager.domain.dto.response;

import java.time.LocalDateTime;

public record ApiKeyResponse(
        Long id,
        String name,
        String keyPrefix,
        /** Only present when the key is first created; null on subsequent reads. */
        String rawKey,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {}
