package com.scrummanager.domain.dto.request;

import com.scrummanager.domain.enums.Availability;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank @Size(min = 1, max = 100) String name,
        @NotNull Availability availability
) {}
