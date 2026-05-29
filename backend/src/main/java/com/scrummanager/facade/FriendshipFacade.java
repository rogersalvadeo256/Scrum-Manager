package com.scrummanager.facade;

import com.scrummanager.business.contract.FriendshipBusinessContract;
import com.scrummanager.domain.dto.response.FriendshipResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Friendship;
import com.scrummanager.domain.model.User;
import com.scrummanager.facade.contract.FriendshipFacadeContract;
import com.scrummanager.service.contract.CacheInvalidationContract;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipFacade implements FriendshipFacadeContract {

    private final FriendshipBusinessContract friendshipBusiness;
    private final CacheInvalidationContract cacheInvalidationService;

    public void sendRequest(Long fromId, Long toId) {
        friendshipBusiness.sendRequest(fromId, toId);
        cacheInvalidationService.evictFriendshipCaches();
    }

    public void answerRequest(Long friendshipId, RequestStatus answer, Long currentUserId) {
        friendshipBusiness.answerRequest(friendshipId, answer, currentUserId);
        cacheInvalidationService.evictFriendshipCaches();
    }

    public void removeFriend(Long friendshipId, Long currentUserId) {
        friendshipBusiness.removeFriend(friendshipId, currentUserId);
        cacheInvalidationService.evictFriendshipCaches();
    }

    @Cacheable(cacheNames = "friendships-pending", key = "#userId")
    public List<FriendshipResponse> getPendingRequests(Long userId) {
        List<Friendship> pending = friendshipBusiness.getPendingRequests(userId);
        if (pending.isEmpty()) return List.of();
        Set<Long> userIds = pending.stream()
                .flatMap(f -> java.util.stream.Stream.of(f.getRequestedById(), f.getReceiverId()))
                .collect(Collectors.toSet());
        Map<Long, String> usernames = friendshipBusiness.getUsernames(userIds);
        return pending.stream().map(f -> toResponse(f, usernames)).toList();
    }

    @Cacheable(cacheNames = "friendships-list", key = "#userId")
    public List<UserResponse> getFriends(Long userId) {
        return friendshipBusiness.getFriends(userId).stream()
                .map(FriendshipFacade::toUserResponse).toList();
    }

    private static FriendshipResponse toResponse(Friendship f, Map<Long, String> usernames) {
        return new FriendshipResponse(f.getId(), f.getRequestedById(),
                usernames.getOrDefault(f.getRequestedById(), "unknown"),
                f.getReceiverId(), usernames.getOrDefault(f.getReceiverId(), "unknown"),
                f.getStatus(), f.getSentDate());
    }

    private static UserResponse toUserResponse(User u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getStatus(),
                u.getRegistrationDate(), u.getProfile().getName());
    }
}
