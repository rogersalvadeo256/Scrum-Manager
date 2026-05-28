package com.scrummanager.controller;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.response.FriendshipResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.service.FriendshipService;
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
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request/{targetUserId}")
    public ResponseEntity<Void> sendRequest(@PathVariable Long targetUserId,
                                            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        friendshipService.sendRequest(principal.getId(), targetUserId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{friendshipId}/answer")
    public ResponseEntity<Void> answer(@PathVariable Long friendshipId,
                                       @RequestBody Map<String, String> body,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        RequestStatus answer = RequestStatus.valueOf(body.get("answer"));
        friendshipService.answerRequest(friendshipId, answer, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<Void> remove(@PathVariable Long friendshipId,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        friendshipService.removeFriend(friendshipId, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipResponse>> pending(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(friendshipService.getPendingRequests(principal.getId()));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> friends(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(friendshipService.getFriends(principal.getId()));
    }
}
