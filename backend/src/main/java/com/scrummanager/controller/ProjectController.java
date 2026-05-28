package com.scrummanager.controller;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.request.ProjectRequest;
import com.scrummanager.dto.response.ProjectResponse;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(req, principal.getId()));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProjectResponse>> myProjects(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectService.getMyProjects(principal.getId()));
    }

    @GetMapping("/member")
    public ResponseEntity<List<ProjectResponse>> projectsAsMember(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectService.getProjectsAsMember(principal.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectService.update(id, req, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        projectService.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invite/{targetUserId}")
    public ResponseEntity<Void> invite(@PathVariable Long id,
                                       @PathVariable Long targetUserId,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        projectService.inviteMember(id, targetUserId, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/invites/{memberId}")
    public ResponseEntity<Void> answerInvite(@PathVariable Long memberId,
                                             @RequestBody Map<String, String> body,
                                             @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        RequestStatus answer = RequestStatus.valueOf(body.get("answer"));
        projectService.answerInvite(memberId, answer, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invites/pending")
    public ResponseEntity<?> pendingInvites(@AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectService.getPendingInvites(principal.getId()));
    }
}
