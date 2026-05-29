package com.scrummanager.domain.dto.request;

import com.scrummanager.domain.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;

public record AnswerRequest(@NotNull RequestStatus answer) {}
