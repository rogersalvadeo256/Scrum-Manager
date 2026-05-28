package com.scrummanager.business;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Project;
import com.scrummanager.domain.model.ProjectMember;
import com.scrummanager.domain.model.ProjectSprint;
import com.scrummanager.domain.model.ProjectTask;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.ProjectRepository;
import com.scrummanager.repository.ProjectSprintRepository;
import com.scrummanager.repository.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskBusiness {

    private final ProjectTaskRepository taskRepository;
    private final ProjectSprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;

    @Transactional
    public ProjectTask createTask(Long projectId, String title, String text, int points,
                                  Long creatorId, Long executorId,
                                  com.scrummanager.domain.enums.TaskStatus status) {
        assertProjectAccess(projectId, creatorId);
        ProjectTask task = ProjectTask.builder()
                .title(title)
                .text(text)
                .points(points)
                .creatorId(creatorId)
                .executorId(executorId)
                .status(status)
                .projectId(projectId)
                .dateStart(LocalDate.now())
                .build();
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<ProjectTask> getTasksByProject(Long projectId, Long userId) {
        assertProjectAccess(projectId, userId);
        return taskRepository.findByProjectId(projectId);
    }

    @Transactional
    public ProjectTask updateTask(Long projectId, Long taskId, String title, String text,
                                  int points, Long executorId,
                                  com.scrummanager.domain.enums.TaskStatus status, Long userId) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Task does not belong to this project");
        }
        if (!task.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the task creator can edit it");
        }
        task.setTitle(title);
        task.setText(text);
        task.setPoints(points);
        task.setExecutorId(executorId);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Transactional
    public ProjectTask deleteTask(Long projectId, Long taskId, Long userId) {
        ProjectTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Task does not belong to this project");
        }
        if (!task.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the task creator can delete it");
        }
        taskRepository.delete(task);
        return task;
    }

    @Transactional
    public ProjectSprint createSprint(Long projectId, String title, String text, int points,
                                      com.scrummanager.domain.enums.SprintStatus status, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = ProjectSprint.builder()
                .title(title)
                .text(text)
                .projectId(projectId)
                .points(points)
                .status(status)
                .build();
        return sprintRepository.save(sprint);
    }

    @Transactional(readOnly = true)
    public List<ProjectSprint> getSprintsByProject(Long projectId, Long userId) {
        assertProjectAccess(projectId, userId);
        return sprintRepository.findByProjectId(projectId);
    }

    @Transactional
    public ProjectSprint updateSprint(Long projectId, Long sprintId, String title, String text,
                                      int points, com.scrummanager.domain.enums.SprintStatus status,
                                      Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint not found"));
        if (!sprint.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Sprint does not belong to this project");
        }
        sprint.setTitle(title);
        sprint.setText(text);
        sprint.setPoints(points);
        sprint.setStatus(status);
        return sprintRepository.save(sprint);
    }

    @Transactional
    public ProjectSprint deleteSprint(Long projectId, Long sprintId, Long userId) {
        assertProjectAccess(projectId, userId);
        ProjectSprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint not found"));
        if (!sprint.getProjectId().equals(projectId)) {
            throw new AccessDeniedException("Sprint does not belong to this project");
        }
        sprintRepository.delete(sprint);
        return sprint;
    }

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
}
