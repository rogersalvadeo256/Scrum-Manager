package com.scrummanager.facade;

import com.scrummanager.business.contract.UserBusinessContract;
import com.scrummanager.domain.dto.request.ChangePasswordRequest;
import com.scrummanager.domain.dto.request.CreateApiKeyRequest;
import com.scrummanager.domain.dto.request.UpdateProfileRequest;
import com.scrummanager.domain.dto.response.ApiKeyResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.dto.response.UserSettingsResponse;
import com.scrummanager.domain.mapper.UserMapper;
import com.scrummanager.domain.model.User;
import com.scrummanager.facade.contract.UserFacadeContract;
import com.scrummanager.service.contract.CacheInvalidationContract;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade implements UserFacadeContract {

    private final UserBusinessContract userBusiness;
    private final CacheInvalidationContract cacheInvalidationService;

    @Cacheable(cacheNames = "user-by-id", key = "#id")
    public UserResponse getById(Long id) {
        return UserMapper.toResponse(userBusiness.getById(id));
    }

    @Cacheable(cacheNames = "user-search", key = "#name.toLowerCase()")
    public List<UserResponse> searchByName(String name) {
        return userBusiness.searchByName(name).stream().map(UserMapper::toResponse).toList();
    }

    public UserResponse updateProfilePhoto(Long id, byte[] photo, Long requesterId) {
        User user = userBusiness.updateProfilePhoto(id, photo, requesterId);
        cacheInvalidationService.evictUserCaches(user);
        return UserMapper.toResponse(user);
    }

    public UserSettingsResponse getMySettings(Long userId) {
        User user = userBusiness.getById(userId);
        return toSettingsResponse(user);
    }

    public UserSettingsResponse updateProfile(Long id, UpdateProfileRequest req, Long requesterId) {
        User user = userBusiness.updateProfile(id, req, requesterId);
        cacheInvalidationService.evictUserCaches(user);
        return toSettingsResponse(user);
    }

    public void changePassword(Long id, ChangePasswordRequest req, Long requesterId) {
        User user = userBusiness.changePassword(id, req, requesterId);
        cacheInvalidationService.evictUserCaches(user);
    }

    public ApiKeyResponse createApiKey(Long userId, CreateApiKeyRequest req) {
        return userBusiness.createApiKey(userId, req);
    }

    public List<ApiKeyResponse> listApiKeys(Long userId) {
        return userBusiness.listApiKeys(userId);
    }

    public void deleteApiKey(Long userId, Long keyId) {
        userBusiness.deleteApiKey(userId, keyId);
    }

    // ─── helpers ──────────────────────────────────────────────────────────────

    static UserResponse toResponse(User u) {
        return UserMapper.toResponse(u);
    }

    private static UserSettingsResponse toSettingsResponse(User u) {
        return new UserSettingsResponse(
                u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName(), u.getProfile().getAvailability());
    }
}

