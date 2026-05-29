package com.scrummanager.business;

import com.scrummanager.domain.model.User;
import com.scrummanager.domain.model.UserProfile;
import com.scrummanager.domain.enums.AccountStatus;
import com.scrummanager.domain.enums.Availability;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBusinessTests {

    @Mock private UserRepository userRepository;

    private UserBusiness userBusiness;

    @BeforeEach
    void setUp() {
        userBusiness = new UserBusiness(userRepository);
    }

    @Test
    void getByIdReturnsUser() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userBusiness.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("alice", result.getUsername());
    }

    @Test
    void getByIdThrowsWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userBusiness.getById(99L));
    }

    @Test
    void searchByNameDelegatesToRepository() {
        User user = baseUser(2L, "bob");
        when(userRepository.searchByNameOrUsername("bob")).thenReturn(List.of(user));

        List<User> result = userBusiness.searchByName("bob");

        assertEquals(1, result.size());
        assertEquals("bob", result.get(0).getUsername());
        verify(userRepository).searchByNameOrUsername("bob");
    }

    @Test
    void searchByNameReturnsEmptyListWhenNoneFound() {
        when(userRepository.searchByNameOrUsername("nobody")).thenReturn(List.of());

        List<User> result = userBusiness.searchByName("nobody");

        assertTrue(result.isEmpty());
    }

    @Test
    void updateProfilePhotoSucceedsForOwner() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        byte[] photo = new byte[]{1, 2, 3};
        User result = userBusiness.updateProfilePhoto(1L, photo, 1L);

        assertArrayEquals(photo, result.getProfile().getPhoto());
        verify(userRepository).save(user);
    }

    @Test
    void updateProfilePhotoDeniedForDifferentUser() {
        User user = baseUser(1L, "alice");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(AccessDeniedException.class,
                () -> userBusiness.updateProfilePhoto(1L, new byte[]{1, 2, 3}, 99L));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateProfilePhotoThrowsWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userBusiness.updateProfilePhoto(99L, new byte[]{}, 99L));
    }

    private User baseUser(Long id, String username) {
        return User.builder()
                .id(id).username(username).email(username + "@example.com")
                .passwordHash("hash").securityQuestion("Q?").securityAnswerHash("a-hash")
                .registrationDate(LocalDate.now()).status(AccountStatus.ACTIVE)
                .tokenVersion(0).failedLoginAttempts(0)
                .profile(UserProfile.builder().name(username).availability(Availability.AVAILABLE).build())
                .build();
    }
}
