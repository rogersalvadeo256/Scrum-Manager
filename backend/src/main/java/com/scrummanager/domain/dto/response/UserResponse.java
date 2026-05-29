package com.scrummanager.domain.dto.response;

import com.scrummanager.domain.enums.AccountStatus;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String username,
        String email,
        AccountStatus status,
        LocalDate registrationDate,
        String profileName
) {}
