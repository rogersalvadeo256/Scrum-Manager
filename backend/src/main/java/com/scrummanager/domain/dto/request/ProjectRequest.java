package com.scrummanager.domain.dto.request;

import com.scrummanager.domain.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank String name,
        String description,
        String type
) {}
