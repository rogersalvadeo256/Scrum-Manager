package com.scrummanager.domain.dto.response;

import com.scrummanager.domain.enums.SprintStatus;

public record SprintResponse(
        Long id,
        String title,
        String text,
        Long projectId,
        int points,
        SprintStatus status
) {}
