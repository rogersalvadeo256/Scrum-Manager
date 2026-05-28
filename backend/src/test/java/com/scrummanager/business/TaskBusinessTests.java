package com.scrummanager.business;

import com.scrummanager.domain.model.Project;
import com.scrummanager.domain.model.ProjectMember;
import com.scrummanager.domain.model.ProjectSprint;
import com.scrummanager.domain.model.ProjectTask;
import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.enums.SprintStatus;
import com.scrummanager.domain.enums.TaskStatus;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.ProjectRepository;
import com.scrummanager.repository.ProjectSprintRepository;
import com.scrummanager.repository.ProjectTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskBusinessTests {

    @Mock private ProjectTaskRepository taskRepository;
    @Mock private ProjectSprintRepository sprintRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private ProjectMemberRepository memberRepository;

    private TaskBusiness taskBusiness;

    @BeforeEach
    void setUp() {
        taskBusiness = new TaskBusiness(taskRepository, sprintRepository, projectRepository, memberRepository);
    }

    @Test
    void createTaskSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectTask saved = baseTask(5L, 1L, 10L);
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(saved);

        ProjectTask result = taskBusiness.createTask(1L, "Title", "text", 3, 10L, null, TaskStatus.TO_DO);

        assertEquals(5L, result.getId());
        verify(taskRepository).save(any(ProjectTask.class));
    }

    @Test
    void createTaskSucceedsForAcceptedMember() {
        stubMemberProject(1L, 10L, 20L, RequestStatus.ACCEPTED);
        ProjectTask saved = baseTask(5L, 1L, 20L);
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(saved);

        taskBusiness.createTask(1L, "Title", "text", 3, 20L, null, TaskStatus.TO_DO);

        verify(taskRepository).save(any(ProjectTask.class));
    }

    @Test
    void createTaskDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.createTask(1L, "Title", "text", 3, 99L, null, TaskStatus.TO_DO));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void createTaskDeniedForPendingMember() {
        stubMemberProject(1L, 10L, 20L, RequestStatus.ON_HOLD);

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.createTask(1L, "Title", "text", 3, 20L, null, TaskStatus.TO_DO));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updateTaskSucceedsForCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(task);

        ProjectTask result = taskBusiness.updateTask(1L, 5L, "Updated", "new text", 5, null, TaskStatus.DOING, 10L);

        assertEquals("Updated", result.getTitle());
        assertEquals(TaskStatus.DOING, result.getStatus());
    }

    @Test
    void updateTaskDeniedForNonCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.updateTask(1L, 5L, "Hack", "x", 0, null, TaskStatus.TO_DO, 99L));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updateTaskDeniedWhenTaskBelongsToDifferentProject() {
        ProjectTask task = baseTask(5L, 99L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.updateTask(1L, 5L, "Title", "x", 0, null, TaskStatus.TO_DO, 10L));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void deleteTaskSucceedsForCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        taskBusiness.deleteTask(1L, 5L, 10L);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTaskDeniedForNonCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(AccessDeniedException.class, () -> taskBusiness.deleteTask(1L, 5L, 77L));
        verify(taskRepository, never()).delete(any());
    }

    @Test
    void createSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint saved = baseSprint(3L, 1L);
        when(sprintRepository.save(any(ProjectSprint.class))).thenReturn(saved);

        ProjectSprint result = taskBusiness.createSprint(1L, "Sprint 1", "goal", 20, SprintStatus.DOING, 10L);

        assertEquals(3L, result.getId());
        verify(sprintRepository).save(any(ProjectSprint.class));
    }

    @Test
    void createSprintDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.createSprint(1L, "Sprint 1", "goal", 20, SprintStatus.DOING, 99L));
        verify(sprintRepository, never()).save(any());
    }

    @Test
    void updateSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 1L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(any(ProjectSprint.class))).thenReturn(sprint);

        ProjectSprint result = taskBusiness.updateSprint(1L, 3L, "Updated Sprint", "new goal", 30, SprintStatus.DONE, 10L);

        assertEquals("Updated Sprint", result.getTitle());
        assertEquals(SprintStatus.DONE, result.getStatus());
    }

    @Test
    void updateSprintDeniedWhenSprintBelongsToDifferentProject() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 99L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        assertThrows(AccessDeniedException.class,
                () -> taskBusiness.updateSprint(1L, 3L, "Sprint", "goal", 20, SprintStatus.DOING, 10L));
        verify(sprintRepository, never()).save(any());
    }

    @Test
    void deleteSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 1L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        taskBusiness.deleteSprint(1L, 3L, 10L);

        verify(sprintRepository).delete(sprint);
    }

    @Test
    void deleteSprintDeniedWhenSprintBelongsToDifferentProject() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 99L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        assertThrows(AccessDeniedException.class, () -> taskBusiness.deleteSprint(1L, 3L, 10L));
        verify(sprintRepository, never()).delete(any());
    }

    private void stubCreatorProject(Long projectId, Long creatorId) {
        Project project = Project.builder()
                .id(projectId).creatorId(creatorId).name("P")
                .dateStart(LocalDate.now()).status(ProjectStatus.IN_PROGRESS)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
    }

    private void stubMemberProject(Long projectId, Long creatorId, Long memberId, RequestStatus memberStatus) {
        Project project = Project.builder()
                .id(projectId).creatorId(creatorId).name("P")
                .dateStart(LocalDate.now()).status(ProjectStatus.IN_PROGRESS)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        if (memberStatus != null) {
            ProjectMember member = ProjectMember.builder()
                    .id(1L).projectId(projectId).userId(memberId)
                    .invitedById(creatorId).inviteStatus(memberStatus).scrumMaster(false).build();
            when(memberRepository.findByProjectIdAndUserId(projectId, memberId))
                    .thenReturn(Optional.of(member));
        } else {
            when(memberRepository.findByProjectIdAndUserId(projectId, memberId))
                    .thenReturn(Optional.empty());
        }
    }

    private ProjectTask baseTask(Long id, Long projectId, Long creatorId) {
        return ProjectTask.builder()
                .id(id).title("Task").text("text").points(3)
                .creatorId(creatorId).status(TaskStatus.TO_DO)
                .projectId(projectId).dateStart(LocalDate.now()).build();
    }

    private ProjectSprint baseSprint(Long id, Long projectId) {
        return ProjectSprint.builder()
                .id(id).title("Sprint 1").text("goal")
                .projectId(projectId).points(20).status(SprintStatus.DOING).build();
    }
}
