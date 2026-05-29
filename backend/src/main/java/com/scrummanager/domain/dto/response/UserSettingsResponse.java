package com.scrummanager.domain.dto.response;

import com.scrummanager.domain.enums.Availability;
import com.scrummanager.domain.enums.AccountStatus;

import java.time.LocalDate;

public record UserSettingsResponse(
        Long id,
        String username,
        String email,
        AccountStatus status,
        LocalDate registrationDate,
        String profileName,
        Availability availability
) {}
