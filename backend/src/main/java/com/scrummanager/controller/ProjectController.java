package com.scrummanager.controller;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.request.ProjectRequest;
import com.scrummanager.dto.response.ProjectResponse;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    private Long resolveUserId(UserDetails principal) {
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow().getId();
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(req, resolveUserId(principal)));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProjectResponse>> myProjects(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(projectService.getMyProjects(resolveUserId(principal)));
    }

    @GetMapping("/member")
    public ResponseEntity<List<ProjectResponse>> projectsAsMember(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(projectService.getProjectsAsMember(resolveUserId(principal)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProjectRequest req,
                                                  @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(projectService.update(id, req, resolveUserId(principal)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails principal) {
        projectService.delete(id, resolveUserId(principal));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/invite/{targetUserId}")
    public ResponseEntity<Void> invite(@PathVariable Long id,
                                       @PathVariable Long targetUserId,
                                       @AuthenticationPrincipal UserDetails principal) {
        projectService.inviteMember(id, targetUserId, resolveUserId(principal));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/invites/{memberId}")
    public ResponseEntity<Void> answerInvite(@PathVariable Long memberId,
                                             @RequestBody Map<String, String> body,
                                             @AuthenticationPrincipal UserDetails principal) {
        RequestStatus answer = RequestStatus.valueOf(body.get("answer"));
        projectService.answerInvite(memberId, answer, resolveUserId(principal));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invites/pending")
    public ResponseEntity<?> pendingInvites(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(projectService.getPendingInvites(resolveUserId(principal)));
    }
}
