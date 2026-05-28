package com.scrummanager.domain.dto.request;

import com.scrummanager.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotBlank String title,
        String text,
        int points,
        Long executorId,
        @NotNull TaskStatus status
) {}
