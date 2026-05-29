package com.scrummanager.business.contract;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Friendship;
import com.scrummanager.domain.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FriendshipBusinessContract {
    Friendship sendRequest(Long fromId, Long toId);
    Friendship answerRequest(Long friendshipId, RequestStatus answer, Long currentUserId);
    Friendship removeFriend(Long friendshipId, Long currentUserId);
    List<Friendship> getPendingRequests(Long userId);
    List<User> getFriends(Long userId);
    Map<Long, String> getUsernames(Set<Long> userIds);
}
