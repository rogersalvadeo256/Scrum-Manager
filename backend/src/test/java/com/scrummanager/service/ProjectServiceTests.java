package com.scrummanager.service;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.request.ProjectRequest;
import com.scrummanager.dto.response.ProjectResponse;
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
class ProjectServiceTests {

    @Mock private ProjectRepository projectRepository;
    @Mock private ProjectMemberRepository memberRepository;
    @Mock private ProjectTaskRepository taskRepository;
    @Mock private ProjectSprintRepository sprintRepository;
    @Mock private CacheInvalidationService cacheInvalidationService;
    @Mock private DomainEventPublisher domainEventPublisher;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, memberRepository,
                taskRepository, sprintRepository, cacheInvalidationService, domainEventPublisher);
    }

    @Test
    void createProjectPersistsAndReturnsResponse() {
        ProjectRequest req = new ProjectRequest("Alpha", "description", "SCRUM");
        Project saved = Project.builder()
                .id(1L).name("Alpha").description("description")
                .type("SCRUM").creatorId(10L)
                .dateStart(LocalDate.now()).status(ProjectStatus.IN_PROGRESS)
                .build();
        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        ProjectResponse response = projectService.create(req, 10L);

        assertEquals(1L, response.id());
        assertEquals("Alpha", response.name());
        assertEquals(10L, response.creatorId());
        verify(projectRepository).save(any(Project.class));
        verify(cacheInvalidationService).evictProjectCaches();
    }

    @Test
    void getMyProjectsReturnsOnlyNonDeletedProjects() {
        Project active = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findByCreatorIdAndStatusNot(10L, ProjectStatus.DELETED))
                .thenReturn(List.of(active));

        List<ProjectResponse> result = projectService.getMyProjects(10L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
    }

    @Test
    void updateProjectSucceedsForCreator() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectRequest req = new ProjectRequest("Updated", "new desc", "KANBAN");
        ProjectResponse response = projectService.update(1L, req, 10L);

        assertEquals("Updated", response.name());
        verify(projectRepository).save(project);
    }

    @Test
    void updateProjectThrowsForNonCreator() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectRequest req = new ProjectRequest("Hack", "x", "SCRUM");
        assertThrows(AccessDeniedException.class, () -> projectService.update(1L, req, 99L));
        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteProjectSetsDeletedStatus() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.delete(1L, 10L);

        assertEquals(ProjectStatus.DELETED, project.getStatus());
        verify(projectRepository).save(project);
        verify(cacheInvalidationService).evictTaskCaches(1L);
    }

    @Test
    void deleteProjectThrowsForNonCreator() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        assertThrows(AccessDeniedException.class, () -> projectService.delete(1L, 55L));
        verify(projectRepository, never()).save(any());
    }

    @Test
    void inviteMemberSavesInviteForNewUser() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(memberRepository.existsByProjectIdAndUserId(1L, 20L)).thenReturn(false);
        when(memberRepository.save(any(ProjectMember.class))).thenAnswer(i -> {
            ProjectMember m = i.getArgument(0);
            m = ProjectMember.builder().id(5L).projectId(m.getProjectId())
                    .userId(m.getUserId()).invitedById(m.getInvitedById())
                    .inviteStatus(m.getInviteStatus()).inviteSentDate(m.getInviteSentDate())
                    .scrumMaster(false).build();
            return m;
        });

        projectService.inviteMember(1L, 20L, 10L);

        verify(memberRepository).save(any(ProjectMember.class));
        verify(cacheInvalidationService).evictProjectCaches();
    }

    @Test
    void inviteMemberThrowsWhenAlreadyInvited() {
        Project project = baseProject(1L, 10L, ProjectStatus.IN_PROGRESS);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(memberRepository.existsByProjectIdAndUserId(1L, 20L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> projectService.inviteMember(1L, 20L, 10L));
        verify(memberRepository, never()).save(any());
    }

    @Test
    void answerInviteUpdatesStatusForCorrectUser() {
        ProjectMember member = ProjectMember.builder()
                .id(5L).projectId(1L).userId(20L).invitedById(10L)
                .inviteStatus(RequestStatus.ON_HOLD).scrumMaster(false).build();
        when(memberRepository.findById(5L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(ProjectMember.class))).thenReturn(member);

        projectService.answerInvite(5L, RequestStatus.ACCEPTED, 20L);

        assertEquals(RequestStatus.ACCEPTED, member.getInviteStatus());
        assertNotNull(member.getInviteAnsweredDate());
    }

    @Test
    void answerInviteThrowsForWrongUser() {
        ProjectMember member = ProjectMember.builder()
                .id(5L).projectId(1L).userId(20L).invitedById(10L)
                .inviteStatus(RequestStatus.ON_HOLD).scrumMaster(false).build();
        when(memberRepository.findById(5L)).thenReturn(Optional.of(member));

        assertThrows(AccessDeniedException.class,
                () -> projectService.answerInvite(5L, RequestStatus.ACCEPTED, 99L));
        verify(memberRepository, never()).save(any());
    }

    private Project baseProject(Long id, Long creatorId, ProjectStatus status) {
        return Project.builder()
                .id(id).name("Project").description("desc").type("SCRUM")
                .creatorId(creatorId).dateStart(LocalDate.now()).status(status)
                .build();
    }
}
