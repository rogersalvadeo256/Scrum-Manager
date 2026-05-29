package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.response.FriendshipResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.enums.RequestStatus;

import java.util.List;

public interface FriendshipFacadeContract {
    void sendRequest(Long fromId, Long toId);
    void answerRequest(Long friendshipId, RequestStatus answer, Long currentUserId);
    void removeFriend(Long friendshipId, Long currentUserId);
    List<FriendshipResponse> getPendingRequests(Long userId);
    List<UserResponse> getFriends(Long userId);
}
