package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.entity.UserProfile;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock private UserRepository userRepository;
    @Mock private CacheInvalidationService cacheInvalidationService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, cacheInvalidationService);
    }

    @Test
    void getByIdReturnsUserResponse() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getById(1L);

        assertEquals(1L, response.id());
        assertEquals("alice", response.username());
    }

    @Test
    void getByIdThrowsWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getById(99L));
    }

    @Test
    void searchByNameDelegatesToRepository() {
        User user = baseUser(2L, "bob");
        when(userRepository.searchByNameOrUsername("bob")).thenReturn(List.of(user));

        List<UserResponse> result = userService.searchByName("bob");

        assertEquals(1, result.size());
        assertEquals("bob", result.get(0).username());
        verify(userRepository).searchByNameOrUsername("bob");
        verify(userRepository, never()).findAll();
    }

    @Test
    void searchByNameReturnsEmptyListWhenNoneFound() {
        when(userRepository.searchByNameOrUsername("nobody")).thenReturn(List.of());

        List<UserResponse> result = userService.searchByName("nobody");

        assertTrue(result.isEmpty());
    }

    @Test
    void updateProfilePhotoSucceedsForOwner() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        byte[] photo = new byte[]{1, 2, 3};
        UserResponse response = userService.updateProfilePhoto(1L, photo, 1L);

        assertArrayEquals(photo, user.getProfile().getPhoto());
        assertEquals("alice", response.username());
        verify(userRepository).save(user);
        verify(cacheInvalidationService).evictUserCaches(user);
    }

    @Test
    void updateProfilePhotoDeniedForDifferentUser() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        byte[] photo = new byte[]{1, 2, 3};
        assertThrows(AccessDeniedException.class,
                () -> userService.updateProfilePhoto(1L, photo, 99L));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateProfilePhotoThrowsWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.updateProfilePhoto(99L, new byte[]{}, 99L));
    }

    private User baseUser(Long id, String username) {
        return User.builder()
                .id(id)
                .username(username)
                .email(username + "@example.com")
                .passwordHash("hash")
                .securityQuestion("Q?")
                .securityAnswerHash("a-hash")
                .registrationDate(LocalDate.now())
                .status(AccountStatus.ACTIVE)
                .tokenVersion(0)
                .failedLoginAttempts(0)
                .profile(UserProfile.builder()
                        .name(username)
                        .availability(Availability.AVAILABLE)
                        .build())
                .build();
    }
}
