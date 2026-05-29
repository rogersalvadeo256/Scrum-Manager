package com.scrummanager.api;

import com.scrummanager.domain.dto.response.FriendshipResponse;
import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.facade.contract.FriendshipFacadeContract;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friendships")
@RequiredArgsConstructor
public class FriendshipApi {

    private final FriendshipFacadeContract friendshipFacade;

    @PostMapping("/request/{targetUserId}")
    public ResponseEntity<Void> sendRequest(@PathVariable Long targetUserId,
                                            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        friendshipFacade.sendRequest(principal.getId(), targetUserId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{friendshipId}/answer")
    public ResponseEntity<Void> answer(@PathVariable Long friendshipId,
                                       @RequestBody Map<String, String> body,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        RequestStatus answer = RequestStatus.valueOf(body.get("answer"));
        friendshipFacade.answerRequest(friendshipId, answer, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<Void> remove(@PathVariable Long friendshipId,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        friendshipFacade.removeFriend(friendshipId, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipResponse>> pending(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(friendshipFacade.getPendingRequests(principal.getId()));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> friends(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(friendshipFacade.getFriends(principal.getId()));
    }
}
