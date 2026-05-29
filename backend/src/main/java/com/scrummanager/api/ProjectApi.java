package com.scrummanager.api;

import com.scrummanager.domain.dto.request.AnswerRequest;
import com.scrummanager.domain.dto.request.ProjectRequest;
import com.scrummanager.domain.dto.response.ProjectMetricsResponse;
import com.scrummanager.domain.dto.response.ProjectResponse;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.facade.contract.ProjectFacadeContract;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectApi {

    private final ProjectFacadeContract projectFacade;

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectFacade.create(req, principal.getId()));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProjectResponse>> myProjects(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectFacade.getMyProjects(principal.getId()));
    }

    @GetMapping("/member")
    public ResponseEntity<List<ProjectResponse>> projectsAsMember(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectFacade.getProjectsAsMember(principal.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectFacade.update(id, req, principal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        projectFacade.delete(id, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invite/{targetUserId}")
    public ResponseEntity<Void> invite(@PathVariable Long id,
                                       @PathVariable Long targetUserId,
                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        projectFacade.inviteMember(id, targetUserId, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/invites/{memberId}")
    public ResponseEntity<Void> answerInvite(@PathVariable Long memberId,
                                             @Valid @RequestBody AnswerRequest body,
                                             @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        projectFacade.answerInvite(memberId, body.answer(), principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invites/pending")
    public ResponseEntity<?> pendingInvites(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectFacade.getPendingInvites(principal.getId()));
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<ProjectMetricsResponse> metrics(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(projectFacade.getProjectMetrics(id, principal.getId()));
    }
}
