package com.scrummanager.controller;

import com.scrummanager.dto.request.SprintRequest;
import com.scrummanager.dto.request.TaskRequest;
import com.scrummanager.dto.response.SprintResponse;
import com.scrummanager.dto.response.TaskResponse;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import com.scrummanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ── Tasks ──────────────────────────────────────────────────────────────

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(projectId, req, principal.getId()));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long projectId,
                                                   @PathVariable Long taskId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskService.updateTask(projectId, taskId, req, principal.getId()));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId,
                                           @PathVariable Long taskId,
                                           @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        taskService.deleteTask(projectId, taskId, principal.getId());
        return ResponseEntity.noContent().build();
    }

    // ── Sprints ────────────────────────────────────────────────────────────

    @PostMapping("/sprints")
    public ResponseEntity<SprintResponse> createSprint(@PathVariable Long projectId,
                                                       @Valid @RequestBody SprintRequest req,
                                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createSprint(projectId, req, principal.getId()));
    }

    @GetMapping("/sprints")
    public ResponseEntity<List<SprintResponse>> getSprints(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getSprintsByProject(projectId));
    }

    @PutMapping("/sprints/{sprintId}")
    public ResponseEntity<SprintResponse> updateSprint(@PathVariable Long projectId,
                                                       @PathVariable Long sprintId,
                                                       @Valid @RequestBody SprintRequest req,
                                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskService.updateSprint(projectId, sprintId, req, principal.getId()));
    }

    @DeleteMapping("/sprints/{sprintId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long projectId,
                                             @PathVariable Long sprintId,
                                             @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        taskService.deleteSprint(projectId, sprintId, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
