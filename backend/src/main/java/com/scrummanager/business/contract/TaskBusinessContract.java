package com.scrummanager.business.contract;

import com.scrummanager.domain.enums.SprintStatus;
import com.scrummanager.domain.enums.TaskStatus;
import com.scrummanager.domain.model.ProjectSprint;
import com.scrummanager.domain.model.ProjectTask;

import java.util.List;

public interface TaskBusinessContract {
    ProjectTask createTask(Long projectId, String title, String text, int points, Long creatorId, Long executorId, TaskStatus status);
    List<ProjectTask> getTasksByProject(Long projectId, Long userId);
    ProjectTask updateTask(Long projectId, Long taskId, String title, String text, int points, Long executorId, TaskStatus status, Long userId);
    ProjectTask deleteTask(Long projectId, Long taskId, Long userId);
    ProjectSprint createSprint(Long projectId, String title, String text, int points, SprintStatus status, Long userId);
    List<ProjectSprint> getSprintsByProject(Long projectId, Long userId);
    ProjectSprint updateSprint(Long projectId, Long sprintId, String title, String text, int points, SprintStatus status, Long userId);
    ProjectSprint deleteSprint(Long projectId, Long sprintId, Long userId);
}
