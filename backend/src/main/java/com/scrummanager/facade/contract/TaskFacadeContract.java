package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.request.SprintRequest;
import com.scrummanager.domain.dto.request.TaskRequest;
import com.scrummanager.domain.dto.response.SprintResponse;
import com.scrummanager.domain.dto.response.TaskResponse;

import java.util.List;

public interface TaskFacadeContract {
    TaskResponse createTask(Long projectId, TaskRequest req, Long userId);
    List<TaskResponse> getTasksByProject(Long projectId, Long userId);
    TaskResponse updateTask(Long projectId, Long taskId, TaskRequest req, Long userId);
    void deleteTask(Long projectId, Long taskId, Long userId);
    SprintResponse createSprint(Long projectId, SprintRequest req, Long userId);
    List<SprintResponse> getSprintsByProject(Long projectId, Long userId);
    SprintResponse updateSprint(Long projectId, Long sprintId, SprintRequest req, Long userId);
    void deleteSprint(Long projectId, Long sprintId, Long userId);
}
