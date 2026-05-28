package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> searchByName(String name) {
        return userRepository.findAll().stream()
                .filter(u -> u.getProfile().getName().toLowerCase().contains(name.toLowerCase())
                        || u.getUsername().toLowerCase().contains(name.toLowerCase()))
                .map(UserService::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateProfilePhoto(Long id, byte[] photo) {
        User user = findOrThrow(id);
        user.getProfile().setPhoto(photo);
        userRepository.save(user);
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
