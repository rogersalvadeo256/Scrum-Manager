package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.request.ChangePasswordRequest;
import com.scrummanager.domain.dto.request.CreateApiKeyRequest;
import com.scrummanager.domain.dto.request.UpdateProfileRequest;
import com.scrummanager.domain.dto.response.ApiKeyResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.dto.response.UserSettingsResponse;

import java.util.List;

public interface UserFacadeContract {
    UserResponse getById(Long id);
    List<UserResponse> searchByName(String name);
    UserResponse updateProfilePhoto(Long id, byte[] photo, Long requesterId);

    UserSettingsResponse getMySettings(Long userId);
    UserSettingsResponse updateProfile(Long id, UpdateProfileRequest req, Long requesterId);
    void changePassword(Long id, ChangePasswordRequest req, Long requesterId);
    ApiKeyResponse createApiKey(Long userId, CreateApiKeyRequest req);
    List<ApiKeyResponse> listApiKeys(Long userId);
    void deleteApiKey(Long userId, Long keyId);
}
