package com.scrummanager.service;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.entity.ProjectSprint;
import com.scrummanager.domain.entity.ProjectTask;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.request.SprintRequest;
import com.scrummanager.dto.request.TaskRequest;
import com.scrummanager.dto.response.SprintResponse;
import com.scrummanager.dto.response.TaskResponse;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.ProjectRepository;
import com.scrummanager.repository.ProjectSprintRepository;
import com.scrummanager.repository.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ProjectTaskRepository taskRepository;
    private final ProjectSprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;
    private final CacheInvalidationService cacheInvalidationService;
    private final DomainEventPublisher domainEventPublisher;

    // ── Tasks ──────────────────────────────────────────────────────────────

    @Transactional
    public TaskResponse createTask(Long projectId, TaskRequest req, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectTask task = ProjectTask.builder()
                .title(req.title())
                .text(req.text())
                .points(req.points())
                .creatorId(userId)
                .executorId(req.executorId())
                .status(req.status())
                .projectId(projectId)
                .dateStart(LocalDate.now())
                .build();
        ProjectTask savedTask = taskRepository.save(task);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish(
                "project.task.created",
                userId,
                "task",
                savedTask.getId(),
                Map.of("projectId", projectId, "status", savedTask.getStatus().name()));
        return toTaskResponse(savedTask);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "project-tasks", key = "#projectId")
    public List<TaskResponse> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream().map(TaskService::toTaskResponse).toList();
    }

    @Transactional
    public TaskResponse updateTask(Long projectId, Long taskId, TaskRequest req, Long userId) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Task does not belong to this project");
        }
        if (!task.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the task creator can edit it");
        }
        task.setTitle(req.title());
        task.setText(req.text());
        task.setPoints(req.points());
        task.setExecutorId(req.executorId());
        task.setStatus(req.status());
        ProjectTask savedTask = taskRepository.save(task);
        cacheInvalidationService.evictTaskCaches(savedTask.getProjectId());
        domainEventPublisher.publish(
                "project.task.updated",
                userId,
                "task",
                savedTask.getId(),
                Map.of("projectId", savedTask.getProjectId(), "status", savedTask.getStatus().name()));
        return toTaskResponse(savedTask);
    }

    @Transactional
    public void deleteTask(Long projectId, Long taskId, Long userId) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Task does not belong to this project");
        }
        if (!task.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the task creator can delete it");
        }
        taskRepository.delete(task);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.task.deleted", userId, "task", taskId, Map.of("projectId", projectId));
    }

    // ── Sprints ────────────────────────────────────────────────────────────

    @Transactional
    public SprintResponse createSprint(Long projectId, SprintRequest req, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = ProjectSprint.builder()
                .title(req.title())
                .text(req.text())
                .projectId(projectId)
                .points(req.points())
                .status(req.status())
                .build();
        ProjectSprint savedSprint = sprintRepository.save(sprint);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.sprint.created", userId, "sprint", savedSprint.getId(), Map.of("projectId", projectId));
        return toSprintResponse(savedSprint);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "project-sprints", key = "#projectId")
    public List<SprintResponse> getSprintsByProject(Long projectId) {
        return sprintRepository.findByProjectId(projectId)
                .stream().map(TaskService::toSprintResponse).toList();
    }

    @Transactional
    public SprintResponse updateSprint(Long projectId, Long sprintId, SprintRequest req, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint not found"));
        if (!sprint.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Sprint does not belong to this project");
        }
        sprint.setTitle(req.title());
        sprint.setText(req.text());
        sprint.setPoints(req.points());
        sprint.setStatus(req.status());
        ProjectSprint savedSprint = sprintRepository.save(sprint);
        cacheInvalidationService.evictTaskCaches(savedSprint.getProjectId());
        domainEventPublisher.publish("project.sprint.updated", userId, "sprint", savedSprint.getId(), Map.of("projectId", savedSprint.getProjectId()));
        return toSprintResponse(savedSprint);
    }

    @Transactional
    public void deleteSprint(Long projectId, Long sprintId, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint not found"));
        if (!sprint.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Sprint does not belong to this project");
        }
        sprintRepository.delete(sprint);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.sprint.deleted", userId, "sprint", sprintId, Map.of("projectId", projectId));
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private void assertProjectAccess(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        if (project.getCreatorId().equals(userId)) {
            return;
        }
        Optional<ProjectMember> membership = memberRepository.findByProjectIdAndUserId(projectId, userId);
        if (membership.isEmpty() || membership.get().getInviteStatus() != RequestStatus.ACCEPTED) {
            throw new AccessDeniedException("You are not a member of this project");
        }
    }

    // ── Mappers ────────────────────────────────────────────────────────────

    static TaskResponse toTaskResponse(ProjectTask t) {
        return new TaskResponse(t.getId(), t.getTitle(), t.getText(), t.getPoints(),
                t.getCreatorId(), t.getExecutorId(), t.getStatus(), t.getProjectId(), t.getDateStart());
    }

    static SprintResponse toSprintResponse(ProjectSprint s) {
        return new SprintResponse(s.getId(), s.getTitle(), s.getText(),
                s.getProjectId(), s.getPoints(), s.getStatus());
    }
}
