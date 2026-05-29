package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.response.UserResponse;

import java.util.List;

public interface UserFacadeContract {
    UserResponse getById(Long id);
    List<UserResponse> searchByName(String name);
    UserResponse updateProfilePhoto(Long id, byte[] photo, Long requesterId);
}
