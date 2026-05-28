package com.scrummanager.service;

import com.scrummanager.domain.entity.Friendship;
import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.response.FriendshipResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.repository.FriendshipRepository;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final CacheInvalidationService cacheInvalidationService;

    @Transactional
    public void sendRequest(Long fromId, Long toId) {
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot send a friend request to yourself");
        }
        friendshipRepository.findPendingBetween(fromId, toId)
                .ifPresent(f -> { throw new IllegalStateException("Request already pending"); });

        Friendship friendship = Friendship.builder()
                .requestedById(fromId)
                .receiverId(toId)
                .status(RequestStatus.ON_HOLD)
                .sentDate(LocalDate.now())
                .build();
        friendshipRepository.save(friendship);
        cacheInvalidationService.evictFriendshipCaches();
    }

    @Transactional
    public void answerRequest(Long friendshipId, RequestStatus answer, Long currentUserId) {
        Friendship f = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!f.getReceiverId().equals(currentUserId)) {
            throw new AccessDeniedException("Not your friendship request");
        }
        f.setStatus(answer);
        friendshipRepository.save(f);
        cacheInvalidationService.evictFriendshipCaches();
    }

    @Transactional
    public void removeFriend(Long friendshipId, Long currentUserId) {
        Friendship f = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        if (!f.getRequestedById().equals(currentUserId) && !f.getReceiverId().equals(currentUserId)) {
            throw new AccessDeniedException("Not your friendship");
        }
        f.setStatus(RequestStatus.REMOVED);
        friendshipRepository.save(f);
        cacheInvalidationService.evictFriendshipCaches();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "friendships-pending", key = "#userId")
    public List<FriendshipResponse> getPendingRequests(Long userId) {
        List<Friendship> pendingRequests = friendshipRepository.findByReceiverIdAndStatus(userId, RequestStatus.ON_HOLD);
        if (pendingRequests.isEmpty()) {
            return List.of();
        }
        Set<Long> userIds = pendingRequests.stream()
                .flatMap(f -> java.util.stream.Stream.of(f.getRequestedById(), f.getReceiverId()))
                .collect(Collectors.toSet());
        Map<Long, String> usernames = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, User::getUsername));
        return pendingRequests.stream()
                .map(f -> toResponse(f, usernames))
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "friendships-list", key = "#userId")
    public List<UserResponse> getFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAcceptedFriendships(userId);
        if (friendships.isEmpty()) {
            return List.of();
        }
        Set<Long> friendIds = friendships.stream()
                .map(f -> f.getRequestedById().equals(userId) ? f.getReceiverId() : f.getRequestedById())
                .collect(Collectors.toSet());
        Map<Long, UserResponse> usersById = userRepository.findAllById(friendIds).stream()
                .map(UserService::toResponse)
                .collect(Collectors.toMap(UserResponse::id, Function.identity()));
        return friendships.stream()
                .map(f -> {
                    Long friendId = f.getRequestedById().equals(userId) ? f.getReceiverId() : f.getRequestedById();
                    return usersById.get(friendId);
                })
                .filter(u -> u != null)
                .toList();
    }

    private FriendshipResponse toResponse(Friendship f, Map<Long, String> usernames) {
        String reqByUsername = usernames.getOrDefault(f.getRequestedById(), "unknown");
        String receiverUsername = usernames.getOrDefault(f.getReceiverId(), "unknown");
        return new FriendshipResponse(f.getId(), f.getRequestedById(), reqByUsername,
                f.getReceiverId(), receiverUsername, f.getStatus(), f.getSentDate());
    }
}
