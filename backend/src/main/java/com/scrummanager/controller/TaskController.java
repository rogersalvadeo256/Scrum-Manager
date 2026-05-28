package com.scrummanager.controller;

import com.scrummanager.dto.request.SprintRequest;
import com.scrummanager.dto.request.TaskRequest;
import com.scrummanager.dto.response.SprintResponse;
import com.scrummanager.dto.response.TaskResponse;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    private Long resolveUserId(UserDetails principal) {
        return userRepository.findByUsername(principal.getUsername()).orElseThrow().getId();
    }

    // ── Tasks ──────────────────────────────────────────────────────────────

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(projectId, req, resolveUserId(principal)));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long projectId,
                                                   @PathVariable Long taskId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(taskService.updateTask(taskId, req, resolveUserId(principal)));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId,
                                           @PathVariable Long taskId,
                                           @AuthenticationPrincipal UserDetails principal) {
        taskService.deleteTask(taskId, resolveUserId(principal));
        return ResponseEntity.noContent().build();
    }

    // ── Sprints ────────────────────────────────────────────────────────────

    @PostMapping("/sprints")
    public ResponseEntity<SprintResponse> createSprint(@PathVariable Long projectId,
                                                       @Valid @RequestBody SprintRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createSprint(projectId, req));
    }

    @GetMapping("/sprints")
    public ResponseEntity<List<SprintResponse>> getSprints(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getSprintsByProject(projectId));
    }

    @PutMapping("/sprints/{sprintId}")
    public ResponseEntity<SprintResponse> updateSprint(@PathVariable Long projectId,
                                                       @PathVariable Long sprintId,
                                                       @Valid @RequestBody SprintRequest req) {
        return ResponseEntity.ok(taskService.updateSprint(sprintId, req));
    }

    @DeleteMapping("/sprints/{sprintId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long projectId,
                                             @PathVariable Long sprintId) {
        taskService.deleteSprint(sprintId);
        return ResponseEntity.noContent().build();
    }
}
