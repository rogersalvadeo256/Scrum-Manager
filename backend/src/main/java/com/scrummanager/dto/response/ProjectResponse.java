package com.scrummanager.dto.response;

import com.scrummanager.domain.enums.ProjectStatus;

import java.time.LocalDate;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        Long creatorId,
        LocalDate dateStart,
        ProjectStatus status,
        String type
) {}
