package com.scrummanager.business;

import com.scrummanager.business.contract.UserBusinessContract;
import com.scrummanager.domain.model.User;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBusiness implements UserBusinessContract {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return findOrThrow(id);
    }

    @Transactional(readOnly = true)
    public List<User> searchByName(String name) {
        return userRepository.searchByNameOrUsername(name);
    }

    @Transactional
    public User updateProfilePhoto(Long id, byte[] photo, Long requesterId) {
        User user = findOrThrow(id);
        if (!user.getId().equals(requesterId)) {
            throw new AccessDeniedException("You can only update your own profile photo");
        }
        user.getProfile().setPhoto(photo);
        return userRepository.save(user);
    }

    public User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }
}
