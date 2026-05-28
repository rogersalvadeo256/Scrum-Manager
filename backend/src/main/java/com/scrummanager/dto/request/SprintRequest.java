package com.scrummanager.dto.request;

import com.scrummanager.domain.enums.SprintStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SprintRequest(
        @NotBlank String title,
        String text,
        int points,
        @NotNull SprintStatus status
) {}
