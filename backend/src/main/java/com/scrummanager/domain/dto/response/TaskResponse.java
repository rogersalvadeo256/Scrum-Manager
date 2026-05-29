package com.scrummanager.domain.dto.response;

import com.scrummanager.domain.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String text,
        int points,
        Long creatorId,
        Long executorId,
        TaskStatus status,
        Long projectId,
        LocalDate dateStart
) {}
