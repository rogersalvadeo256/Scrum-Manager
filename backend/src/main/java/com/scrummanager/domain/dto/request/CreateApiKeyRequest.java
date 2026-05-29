package com.scrummanager.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateApiKeyRequest(
        @NotBlank @Size(min = 1, max = 100) String name,
        /** Optional: number of days until the key expires. Null means no expiry. */
        @Positive Integer expiresInDays
) {}
