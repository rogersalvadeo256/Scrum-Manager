package com.scrummanager.service;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.entity.ProjectSprint;
import com.scrummanager.domain.entity.ProjectTask;
import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.enums.SprintStatus;
import com.scrummanager.domain.enums.TaskStatus;
import com.scrummanager.dto.request.SprintRequest;
import com.scrummanager.dto.request.TaskRequest;
import com.scrummanager.dto.response.SprintResponse;
import com.scrummanager.dto.response.TaskResponse;
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
class TaskServiceTests {

    @Mock private ProjectTaskRepository taskRepository;
    @Mock private ProjectSprintRepository sprintRepository;
    @Mock private ProjectRepository projectRepository;
    @Mock private ProjectMemberRepository memberRepository;
    @Mock private CacheInvalidationService cacheInvalidationService;
    @Mock private DomainEventPublisher domainEventPublisher;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, sprintRepository, projectRepository,
                memberRepository, cacheInvalidationService, domainEventPublisher);
    }

    // ── Task creation ──────────────────────────────────────────────────────

    @Test
    void createTaskSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectTask saved = baseTask(5L, 1L, 10L);
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(saved);

        TaskRequest req = new TaskRequest("Title", "text", 3, null, TaskStatus.TO_DO);
        TaskResponse response = taskService.createTask(1L, req, 10L);

        assertEquals(5L, response.id());
        verify(taskRepository).save(any(ProjectTask.class));
    }

    @Test
    void createTaskSucceedsForAcceptedMember() {
        stubMemberProject(1L, 10L, 20L, RequestStatus.ACCEPTED);
        ProjectTask saved = baseTask(5L, 1L, 20L);
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(saved);

        TaskRequest req = new TaskRequest("Title", "text", 3, null, TaskStatus.TO_DO);
        taskService.createTask(1L, req, 20L);

        verify(taskRepository).save(any(ProjectTask.class));
    }

    @Test
    void createTaskDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        TaskRequest req = new TaskRequest("Title", "text", 3, null, TaskStatus.TO_DO);
        assertThrows(AccessDeniedException.class, () -> taskService.createTask(1L, req, 99L));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void createTaskDeniedForPendingMember() {
        stubMemberProject(1L, 10L, 20L, RequestStatus.ON_HOLD);

        TaskRequest req = new TaskRequest("Title", "text", 3, null, TaskStatus.TO_DO);
        assertThrows(AccessDeniedException.class, () -> taskService.createTask(1L, req, 20L));
        verify(taskRepository, never()).save(any());
    }

    // ── Task update ────────────────────────────────────────────────────────

    @Test
    void updateTaskSucceedsForCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(ProjectTask.class))).thenReturn(task);

        TaskRequest req = new TaskRequest("Updated", "new text", 5, null, TaskStatus.DOING);
        TaskResponse response = taskService.updateTask(1L, 5L, req, 10L);

        assertEquals("Updated", response.title());
        assertEquals(TaskStatus.DOING, response.status());
    }

    @Test
    void updateTaskDeniedForNonCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        TaskRequest req = new TaskRequest("Hack", "x", 0, null, TaskStatus.TO_DO);
        assertThrows(AccessDeniedException.class, () -> taskService.updateTask(1L, 5L, req, 99L));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void updateTaskDeniedWhenTaskBelongsToDifferentProject() {
        ProjectTask task = baseTask(5L, 99L, 10L); // task belongs to project 99, not 1
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        TaskRequest req = new TaskRequest("Title", "x", 0, null, TaskStatus.TO_DO);
        assertThrows(AccessDeniedException.class, () -> taskService.updateTask(1L, 5L, req, 10L));
        verify(taskRepository, never()).save(any());
    }

    // ── Task delete ────────────────────────────────────────────────────────

    @Test
    void deleteTaskSucceedsForCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, 5L, 10L);

        verify(taskRepository).delete(task);
        verify(cacheInvalidationService).evictTaskCaches(1L);
    }

    @Test
    void deleteTaskDeniedForNonCreator() {
        ProjectTask task = baseTask(5L, 1L, 10L);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(AccessDeniedException.class, () -> taskService.deleteTask(1L, 5L, 77L));
        verify(taskRepository, never()).delete(any());
    }

    @Test
    void deleteTaskDeniedWhenTaskBelongsToDifferentProject() {
        ProjectTask task = baseTask(5L, 99L, 10L); // task belongs to project 99
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(AccessDeniedException.class, () -> taskService.deleteTask(1L, 5L, 10L));
        verify(taskRepository, never()).delete(any());
    }

    // ── Sprint creation ────────────────────────────────────────────────────

    @Test
    void createSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint saved = baseSprint(3L, 1L);
        when(sprintRepository.save(any(ProjectSprint.class))).thenReturn(saved);

        SprintRequest req = new SprintRequest("Sprint 1", "goal", 20, SprintStatus.DOING);
        SprintResponse response = taskService.createSprint(1L, req, 10L);

        assertEquals(3L, response.id());
        verify(sprintRepository).save(any(ProjectSprint.class));
    }

    @Test
    void createSprintDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        SprintRequest req = new SprintRequest("Sprint 1", "goal", 20, SprintStatus.DOING);
        assertThrows(AccessDeniedException.class, () -> taskService.createSprint(1L, req, 99L));
        verify(sprintRepository, never()).save(any());
    }

    // ── Sprint update ──────────────────────────────────────────────────────

    @Test
    void updateSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 1L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(any(ProjectSprint.class))).thenReturn(sprint);

        SprintRequest req = new SprintRequest("Updated Sprint", "new goal", 30, SprintStatus.DONE);
        SprintResponse response = taskService.updateSprint(1L, 3L, req, 10L);

        assertEquals("Updated Sprint", response.title());
        assertEquals(SprintStatus.DONE, response.status());
    }

    @Test
    void updateSprintDeniedWhenSprintBelongsToDifferentProject() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 99L); // sprint belongs to project 99
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        SprintRequest req = new SprintRequest("Sprint", "goal", 20, SprintStatus.DOING);
        assertThrows(AccessDeniedException.class, () -> taskService.updateSprint(1L, 3L, req, 10L));
        verify(sprintRepository, never()).save(any());
    }

    @Test
    void updateSprintDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        SprintRequest req = new SprintRequest("Sprint", "goal", 20, SprintStatus.DOING);
        assertThrows(AccessDeniedException.class, () -> taskService.updateSprint(1L, 3L, req, 99L));
    }

    // ── Sprint delete ──────────────────────────────────────────────────────

    @Test
    void deleteSprintSucceedsForProjectCreator() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 1L);
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        taskService.deleteSprint(1L, 3L, 10L);

        verify(sprintRepository).delete(sprint);
        verify(cacheInvalidationService).evictTaskCaches(1L);
    }

    @Test
    void deleteSprintDeniedWhenSprintBelongsToDifferentProject() {
        stubCreatorProject(1L, 10L);
        ProjectSprint sprint = baseSprint(3L, 99L); // wrong project
        when(sprintRepository.findById(3L)).thenReturn(Optional.of(sprint));

        assertThrows(AccessDeniedException.class, () -> taskService.deleteSprint(1L, 3L, 10L));
        verify(sprintRepository, never()).delete(any());
    }

    @Test
    void deleteSprintDeniedForNonMember() {
        stubMemberProject(1L, 10L, 99L, null);

        assertThrows(AccessDeniedException.class, () -> taskService.deleteSprint(1L, 3L, 99L));
        verify(sprintRepository, never()).delete(any());
    }

    // ── Helpers ────────────────────────────────────────────────────────────

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
                    .invitedById(creatorId).inviteStatus(memberStatus).scrumMaster(false)
                    .build();
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
                .projectId(projectId).dateStart(LocalDate.now())
                .build();
    }

    private ProjectSprint baseSprint(Long id, Long projectId) {
        return ProjectSprint.builder()
                .id(id).title("Sprint 1").text("goal")
                .projectId(projectId).points(20).status(SprintStatus.DOING)
                .build();
    }
}
