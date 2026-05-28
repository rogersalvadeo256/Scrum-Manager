package com.scrummanager.business;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Friendship;
import com.scrummanager.domain.model.User;
import com.scrummanager.repository.FriendshipRepository;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipBusiness {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Transactional
    public Friendship sendRequest(Long fromId, Long toId) {
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
        return friendshipRepository.save(friendship);
    }

    @Transactional
    public Friendship answerRequest(Long friendshipId, RequestStatus answer, Long currentUserId) {
        Friendship f = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        if (!f.getReceiverId().equals(currentUserId)) {
            throw new AccessDeniedException("Not your friendship request");
        }
        f.setStatus(answer);
        return friendshipRepository.save(f);
    }

    @Transactional
    public Friendship removeFriend(Long friendshipId, Long currentUserId) {
        Friendship f = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));
        if (!f.getRequestedById().equals(currentUserId) && !f.getReceiverId().equals(currentUserId)) {
            throw new AccessDeniedException("Not your friendship");
        }
        f.setStatus(RequestStatus.REMOVED);
        return friendshipRepository.save(f);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getPendingRequests(Long userId) {
        return friendshipRepository.findByReceiverIdAndStatus(userId, RequestStatus.ON_HOLD);
    }

    @Transactional(readOnly = true)
    public List<User> getFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAcceptedFriendships(userId);
        if (friendships.isEmpty()) {
            return List.of();
        }
        Set<Long> friendIds = friendships.stream()
                .map(f -> f.getRequestedById().equals(userId) ? f.getReceiverId() : f.getRequestedById())
                .collect(Collectors.toSet());
        Map<Long, User> usersById = userRepository.findAllById(friendIds).stream()
                .collect(Collectors.toMap(User::getId, u -> u));
        return friendships.stream()
                .map(f -> {
                    Long friendId = f.getRequestedById().equals(userId) ? f.getReceiverId() : f.getRequestedById();
                    return usersById.get(friendId);
                })
                .filter(u -> u != null)
                .toList();
    }

    @Transactional(readOnly = true)
    public Map<Long, String> getUsernames(Set<Long> userIds) {
        return userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, User::getUsername));
    }
}
