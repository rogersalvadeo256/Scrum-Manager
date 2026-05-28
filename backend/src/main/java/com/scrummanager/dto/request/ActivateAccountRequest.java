package com.scrummanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivateAccountRequest(
        @NotBlank String usernameOrEmail,
        @NotBlank String securityQuestion,
        @NotBlank String securityAnswer,
        @NotBlank @Size(min = 12, max = 100) String newPassword
) {}
