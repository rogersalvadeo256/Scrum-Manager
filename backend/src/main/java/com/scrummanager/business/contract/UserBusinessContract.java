package com.scrummanager.business.contract;

import com.scrummanager.domain.dto.request.ChangePasswordRequest;
import com.scrummanager.domain.dto.request.CreateApiKeyRequest;
import com.scrummanager.domain.dto.request.UpdateProfileRequest;
import com.scrummanager.domain.dto.response.ApiKeyResponse;
import com.scrummanager.domain.model.User;

import java.util.List;

public interface UserBusinessContract {
    User getById(Long id);
    List<User> searchByName(String name);
    User updateProfilePhoto(Long id, byte[] photo, Long requesterId);
    User findOrThrow(Long id);

    User updateProfile(Long id, UpdateProfileRequest req, Long requesterId);
    User changePassword(Long id, ChangePasswordRequest req, Long requesterId);
    ApiKeyResponse createApiKey(Long userId, CreateApiKeyRequest req);
    List<ApiKeyResponse> listApiKeys(Long userId);
    void deleteApiKey(Long userId, Long keyId);
}
