package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CacheInvalidationService cacheInvalidationService;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "user-by-id", key = "#id")
    public UserResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "user-search", key = "#name.toLowerCase()")
    public List<UserResponse> searchByName(String name) {
        return userRepository.findAll().stream()
                .filter(u -> u.getProfile().getName().toLowerCase().contains(name.toLowerCase())
                        || u.getUsername().toLowerCase().contains(name.toLowerCase()))
                .map(UserService::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateProfilePhoto(Long id, byte[] photo, String username) {
        User user = findOrThrow(id);
        if (!user.getUsername().equals(username)) {
            throw new AccessDeniedException("You can only update your own profile photo");
        }
        user.getProfile().setPhoto(photo);
        userRepository.save(user);
        cacheInvalidationService.evictUserCaches(user);
        return toResponse(user);
    }

    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    static UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }
}
