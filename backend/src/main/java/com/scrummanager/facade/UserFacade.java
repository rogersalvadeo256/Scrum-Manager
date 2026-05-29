package com.scrummanager.facade;

import com.scrummanager.business.contract.UserBusinessContract;
import com.scrummanager.domain.dto.response.UserResponse;
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
        return toResponse(userBusiness.getById(id));
    }

    @Cacheable(cacheNames = "user-search", key = "#name.toLowerCase()")
    public List<UserResponse> searchByName(String name) {
        return userBusiness.searchByName(name).stream().map(UserFacade::toResponse).toList();
    }

    public UserResponse updateProfilePhoto(Long id, byte[] photo, Long requesterId) {
        User user = userBusiness.updateProfilePhoto(id, photo, requesterId);
        cacheInvalidationService.evictUserCaches(user);
        return toResponse(user);
    }

    static UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }
}
