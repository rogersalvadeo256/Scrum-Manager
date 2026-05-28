package com.scrummanager.dto.response;

import com.scrummanager.domain.enums.RequestStatus;

import java.time.LocalDate;

public record FriendshipResponse(
        Long id,
        Long requestedById,
        String requestedByUsername,
        Long receiverId,
        String receiverUsername,
        RequestStatus status,
        LocalDate sentDate
) {}
