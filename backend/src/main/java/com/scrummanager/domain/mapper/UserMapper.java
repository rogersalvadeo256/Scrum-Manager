package com.scrummanager.domain.mapper;

import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.model.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getStatus(),
                u.getRegistrationDate(),
                u.getProfile().getName());
    }
}
