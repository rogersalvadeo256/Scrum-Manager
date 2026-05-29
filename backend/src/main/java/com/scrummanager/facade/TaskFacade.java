package com.scrummanager.facade;

import com.scrummanager.business.TaskBusiness;
import com.scrummanager.domain.dto.request.SprintRequest;
import com.scrummanager.domain.dto.request.TaskRequest;
import com.scrummanager.domain.dto.response.SprintResponse;
import com.scrummanager.domain.dto.response.TaskResponse;
import com.scrummanager.domain.model.ProjectSprint;
import com.scrummanager.domain.model.ProjectTask;
import com.scrummanager.service.CacheInvalidationService;
import com.scrummanager.service.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskBusiness taskBusiness;
    private final CacheInvalidationService cacheInvalidationService;
    private final DomainEventPublisher domainEventPublisher;

    public TaskResponse createTask(Long projectId, TaskRequest req, Long userId) {
        ProjectTask task = taskBusiness.createTask(
                projectId, req.title(), req.text(), req.points(), userId, req.executorId(), req.status());
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.task.created", userId, "task", task.getId(),
                Map.of("projectId", projectId, "status", task.getStatus().name()));
        return toTaskResponse(task);
    }

    @Cacheable(cacheNames = "project-tasks", key = "#projectId + '_' + #userId")
    public List<TaskResponse> getTasksByProject(Long projectId, Long userId) {
        return taskBusiness.getTasksByProject(projectId, userId).stream()
                .map(TaskFacade::toTaskResponse).toList();
    }

    public TaskResponse updateTask(Long projectId, Long taskId, TaskRequest req, Long userId) {
        ProjectTask task = taskBusiness.updateTask(
                projectId, taskId, req.title(), req.text(), req.points(), req.executorId(), req.status(), userId);
        cacheInvalidationService.evictTaskCaches(task.getProjectId());
        domainEventPublisher.publish("project.task.updated", userId, "task", task.getId(),
                Map.of("projectId", task.getProjectId(), "status", task.getStatus().name()));
        return toTaskResponse(task);
    }

    public void deleteTask(Long projectId, Long taskId, Long userId) {
        ProjectTask task = taskBusiness.deleteTask(projectId, taskId, userId);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.task.deleted", userId, "task", taskId,
                Map.of("projectId", projectId));
    }

    public SprintResponse createSprint(Long projectId, SprintRequest req, Long userId) {
        ProjectSprint sprint = taskBusiness.createSprint(
                projectId, req.title(), req.text(), req.points(), req.status(), userId);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.sprint.created", userId, "sprint", sprint.getId(),
                Map.of("projectId", projectId));
        return toSprintResponse(sprint);
    }

    @Cacheable(cacheNames = "project-sprints", key = "#projectId + '_' + #userId")
    public List<SprintResponse> getSprintsByProject(Long projectId, Long userId) {
        return taskBusiness.getSprintsByProject(projectId, userId).stream()
                .map(TaskFacade::toSprintResponse).toList();
    }

    public SprintResponse updateSprint(Long projectId, Long sprintId, SprintRequest req, Long userId) {
        ProjectSprint sprint = taskBusiness.updateSprint(
                projectId, sprintId, req.title(), req.text(), req.points(), req.status(), userId);
        cacheInvalidationService.evictTaskCaches(sprint.getProjectId());
        domainEventPublisher.publish("project.sprint.updated", userId, "sprint", sprint.getId(),
                Map.of("projectId", sprint.getProjectId()));
        return toSprintResponse(sprint);
    }

    public void deleteSprint(Long projectId, Long sprintId, Long userId) {
        ProjectSprint sprint = taskBusiness.deleteSprint(projectId, sprintId, userId);
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.sprint.deleted", userId, "sprint", sprintId,
                Map.of("projectId", projectId));
    }

    static TaskResponse toTaskResponse(ProjectTask t) {
        return new TaskResponse(t.getId(), t.getTitle(), t.getText(), t.getPoints(),
                t.getCreatorId(), t.getExecutorId(), t.getStatus(), t.getProjectId(), t.getDateStart());
    }

    static SprintResponse toSprintResponse(ProjectSprint s) {
        return new SprintResponse(s.getId(), s.getTitle(), s.getText(),
                s.getProjectId(), s.getPoints(), s.getStatus());
    }
}
