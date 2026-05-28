package com.scrummanager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActivateAccountRequest(
        @NotBlank String usernameOrEmail,
        @NotBlank String securityQuestion,
        @NotBlank String securityAnswer,
        @NotBlank String newPassword
) {}
