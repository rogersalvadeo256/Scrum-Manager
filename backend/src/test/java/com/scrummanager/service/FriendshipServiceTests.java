package com.scrummanager.service;

import com.scrummanager.domain.entity.Friendship;
import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.entity.UserProfile;
import com.scrummanager.domain.enums.Availability;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.response.FriendshipResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.FriendshipRepository;
import com.scrummanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTests {

    @Mock private FriendshipRepository friendshipRepository;
    @Mock private UserRepository userRepository;
    @Mock private CacheInvalidationService cacheInvalidationService;

    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        friendshipService = new FriendshipService(friendshipRepository, userRepository, cacheInvalidationService);
    }

    @Test
    void sendRequestCreatesFriendship() {
        when(friendshipRepository.findPendingBetween(1L, 2L)).thenReturn(Optional.empty());
        when(friendshipRepository.save(any(Friendship.class))).thenAnswer(i -> {
            Friendship f = i.getArgument(0);
            return Friendship.builder().id(10L).requestedById(f.getRequestedById())
                    .receiverId(f.getReceiverId()).status(f.getStatus()).sentDate(f.getSentDate())
                    .build();
        });

        friendshipService.sendRequest(1L, 2L);

        verify(friendshipRepository).save(any(Friendship.class));
        verify(cacheInvalidationService).evictFriendshipCaches();
    }

    @Test
    void sendRequestToSelfThrows() {
        assertThrows(IllegalArgumentException.class, () -> friendshipService.sendRequest(1L, 1L));
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void sendRequestWhenAlreadyPendingThrows() {
        Friendship existing = baseFriendship(10L, 1L, 2L, RequestStatus.ON_HOLD);
        when(friendshipRepository.findPendingBetween(1L, 2L)).thenReturn(Optional.of(existing));

        assertThrows(IllegalStateException.class, () -> friendshipService.sendRequest(1L, 2L));
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void answerRequestAcceptsForCorrectReceiver() {
        Friendship friendship = baseFriendship(10L, 1L, 2L, RequestStatus.ON_HOLD);
        when(friendshipRepository.findById(10L)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        friendshipService.answerRequest(10L, RequestStatus.ACCEPTED, 2L);

        assertEquals(RequestStatus.ACCEPTED, friendship.getStatus());
        verify(cacheInvalidationService).evictFriendshipCaches();
    }

    @Test
    void answerRequestThrowsForWrongReceiver() {
        Friendship friendship = baseFriendship(10L, 1L, 2L, RequestStatus.ON_HOLD);
        when(friendshipRepository.findById(10L)).thenReturn(Optional.of(friendship));

        assertThrows(AccessDeniedException.class,
                () -> friendshipService.answerRequest(10L, RequestStatus.ACCEPTED, 99L));
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void removeFriendSetsRemovedStatusForSender() {
        Friendship friendship = baseFriendship(10L, 1L, 2L, RequestStatus.ACCEPTED);
        when(friendshipRepository.findById(10L)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        friendshipService.removeFriend(10L, 1L);

        assertEquals(RequestStatus.REMOVED, friendship.getStatus());
    }

    @Test
    void removeFriendSetsRemovedStatusForReceiver() {
        Friendship friendship = baseFriendship(10L, 1L, 2L, RequestStatus.ACCEPTED);
        when(friendshipRepository.findById(10L)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        friendshipService.removeFriend(10L, 2L);

        assertEquals(RequestStatus.REMOVED, friendship.getStatus());
    }

    @Test
    void removeFriendThrowsForUnrelatedUser() {
        Friendship friendship = baseFriendship(10L, 1L, 2L, RequestStatus.ACCEPTED);
        when(friendshipRepository.findById(10L)).thenReturn(Optional.of(friendship));

        assertThrows(AccessDeniedException.class, () -> friendshipService.removeFriend(10L, 99L));
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void getPendingRequestsReturnsPendingFriendships() {
        Friendship f = baseFriendship(10L, 1L, 2L, RequestStatus.ON_HOLD);
        when(friendshipRepository.findByReceiverIdAndStatus(2L, RequestStatus.ON_HOLD))
                .thenReturn(List.of(f));
        when(userRepository.findAllById(any())).thenReturn(List.of(
                baseUser(1L, "sender"),
                baseUser(2L, "receiver")
        ));

        List<FriendshipResponse> result = friendshipService.getPendingRequests(2L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).id());
        assertEquals("sender", result.get(0).requestedByUsername());
    }

    @Test
    void getFriendsReturnsAcceptedFriends() {
        Friendship f = baseFriendship(10L, 1L, 2L, RequestStatus.ACCEPTED);
        when(friendshipRepository.findAcceptedFriendships(1L)).thenReturn(List.of(f));
        when(userRepository.findAllById(any())).thenReturn(List.of(baseUser(2L, "friend")));

        List<UserResponse> result = friendshipService.getFriends(1L);

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).id());
        assertEquals("friend", result.get(0).username());
    }

    private Friendship baseFriendship(Long id, Long senderId, Long receiverId, RequestStatus status) {
        return Friendship.builder()
                .id(id).requestedById(senderId).receiverId(receiverId)
                .status(status).sentDate(LocalDate.now())
                .build();
    }

    private User baseUser(Long id, String username) {
        return User.builder()
                .id(id).username(username).email(username + "@example.com")
                .profile(UserProfile.builder().name(username).availability(Availability.AVAILABLE).build())
                .build();
    }
}
