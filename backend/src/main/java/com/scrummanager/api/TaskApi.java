package com.scrummanager.api;

import com.scrummanager.domain.dto.request.SprintRequest;
import com.scrummanager.domain.dto.request.TaskRequest;
import com.scrummanager.domain.dto.response.SprintResponse;
import com.scrummanager.domain.dto.response.TaskResponse;
import com.scrummanager.facade.TaskFacade;
import com.scrummanager.security.AuthenticatedUserPrincipal;
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
public class TaskApi {

    private final TaskFacade taskFacade;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createTask(projectId, req, principal.getId()));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long projectId,
                                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskFacade.getTasksByProject(projectId, principal.getId()));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long projectId,
                                                   @PathVariable Long taskId,
                                                   @Valid @RequestBody TaskRequest req,
                                                   @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskFacade.updateTask(projectId, taskId, req, principal.getId()));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId,
                                           @PathVariable Long taskId,
                                           @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        taskFacade.deleteTask(projectId, taskId, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sprints")
    public ResponseEntity<SprintResponse> createSprint(@PathVariable Long projectId,
                                                       @Valid @RequestBody SprintRequest req,
                                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createSprint(projectId, req, principal.getId()));
    }

    @GetMapping("/sprints")
    public ResponseEntity<List<SprintResponse>> getSprints(@PathVariable Long projectId,
                                                           @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskFacade.getSprintsByProject(projectId, principal.getId()));
    }

    @PutMapping("/sprints/{sprintId}")
    public ResponseEntity<SprintResponse> updateSprint(@PathVariable Long projectId,
                                                       @PathVariable Long sprintId,
                                                       @Valid @RequestBody SprintRequest req,
                                                       @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(taskFacade.updateSprint(projectId, sprintId, req, principal.getId()));
    }

    @DeleteMapping("/sprints/{sprintId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long projectId,
                                             @PathVariable Long sprintId,
                                             @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        taskFacade.deleteSprint(projectId, sprintId, principal.getId());
        return ResponseEntity.noContent().build();
    }
}
