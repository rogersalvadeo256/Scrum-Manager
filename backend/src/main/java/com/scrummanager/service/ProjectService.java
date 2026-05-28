package com.scrummanager.service;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.entity.ProjectSprint;
import com.scrummanager.domain.entity.ProjectTask;
import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.enums.SprintStatus;
import com.scrummanager.domain.enums.TaskStatus;
import com.scrummanager.dto.request.ProjectRequest;
import com.scrummanager.dto.response.ProjectMetricsResponse;
import com.scrummanager.dto.response.ProjectResponse;
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
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectSprintRepository sprintRepository;
    private final CacheInvalidationService cacheInvalidationService;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public ProjectResponse create(ProjectRequest req, Long userId) {
        Project project = Project.builder()
                .name(req.name())
                .description(req.description())
                .type(req.type())
                .creatorId(userId)
                .dateStart(LocalDate.now())
                .status(ProjectStatus.IN_PROGRESS)
                .build();
        Project savedProject = projectRepository.save(project);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish(
                "project.created",
                userId,
                "project",
                savedProject.getId(),
                Map.of("name", savedProject.getName()));
        return toResponse(savedProject);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "project-mine", key = "#userId")
    public List<ProjectResponse> getMyProjects(Long userId) {
        return projectRepository.findByCreatorIdAndStatusNot(userId, ProjectStatus.DELETED)
                .stream().map(ProjectService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "project-member", key = "#userId")
    public List<ProjectResponse> getProjectsAsMember(Long userId) {
        List<Long> projectIds = memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ACCEPTED)
                .stream()
                .map(ProjectMember::getProjectId)
                .distinct()
                .toList();
        if (projectIds.isEmpty()) {
            return List.of();
        }
        return projectRepository.findByIdInAndStatusNot(projectIds, ProjectStatus.DELETED)
                .stream()
                .map(ProjectService::toResponse)
                .toList();
    }

    @Transactional
    public ProjectResponse update(Long projectId, ProjectRequest req, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setName(req.name());
        project.setDescription(req.description());
        project.setType(req.type());
        Project savedProject = projectRepository.save(project);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish(
                "project.updated",
                userId,
                "project",
                savedProject.getId(),
                Map.of("name", savedProject.getName()));
        return toResponse(savedProject);
    }

    @Transactional
    public void delete(Long projectId, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setStatus(ProjectStatus.DELETED);
        projectRepository.save(project);
        cacheInvalidationService.evictProjectCaches();
        cacheInvalidationService.evictTaskCaches(projectId);
        domainEventPublisher.publish("project.deleted", userId, "project", projectId, Map.of("status", project.getStatus().name()));
    }

    @Transactional
    public void inviteMember(Long projectId, Long targetUserId, Long invitedById) {
        findAndAuthorize(projectId, invitedById);
        if (memberRepository.existsByProjectIdAndUserId(projectId, targetUserId)) {
            throw new IllegalStateException("User already invited or member");
        }
        ProjectMember member = ProjectMember.builder()
                .projectId(projectId)
                .userId(targetUserId)
                .invitedById(invitedById)
                .inviteSentDate(LocalDate.now())
                .inviteStatus(RequestStatus.ON_HOLD)
                .scrumMaster(false)
                .build();
        memberRepository.save(member);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish(
                "project.member.invited",
                invitedById,
                "project-member",
                member.getId(),
                Map.of("projectId", projectId, "targetUserId", targetUserId));
    }

    @Transactional
    public void answerInvite(Long memberId, RequestStatus answer, Long userId) {
        ProjectMember member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invite not found"));
        if (!member.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not your invitation");
        }
        member.setInviteStatus(answer);
        member.setInviteAnsweredDate(LocalDate.now());
        memberRepository.save(member);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish(
                "project.member.invite-answered",
                userId,
                "project-member",
                memberId,
                Map.of("answer", answer.name(), "projectId", member.getProjectId()));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "project-invites", key = "#userId")
    public List<ProjectMember> getPendingInvites(Long userId) {
        return memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ON_HOLD);
    }

    @Transactional(readOnly = true)
    public ProjectMetricsResponse getProjectMetrics(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        boolean isCreator = project.getCreatorId().equals(userId);
        boolean isMember = memberRepository.existsByProjectIdAndUserIdAndInviteStatus(
                projectId, userId, RequestStatus.ACCEPTED);
        if (!isCreator && !isMember) {
            throw new AccessDeniedException("You are not a member of this project");
        }

        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());

        String currentMonth = now.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                + " " + now.getYear();

        List<ProjectTask> tasks = taskRepository.findByProjectId(projectId);
        List<ProjectSprint> sprints = sprintRepository.findByProjectId(projectId);
        List<ProjectMember> acceptedMembers = memberRepository.findByProjectIdAndInviteStatus(
                projectId, RequestStatus.ACCEPTED);

        int totalTasks = tasks.size();
        long todoCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.TO_DO).count();
        long doingCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DOING).count();
        long doneCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();

        List<ProjectTask> tasksThisMonthList = tasks.stream()
                .filter(t -> t.getDateStart() != null
                        && !t.getDateStart().isBefore(monthStart)
                        && !t.getDateStart().isAfter(monthEnd))
                .toList();
        int tasksThisMonth = tasksThisMonthList.size();
        long doneThisMonth = tasksThisMonthList.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE).count();
        int velocityThisMonth = tasksThisMonthList.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .mapToInt(ProjectTask::getPoints).sum();

        int totalPoints = tasks.stream().mapToInt(ProjectTask::getPoints).sum();
        int completedPoints = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .mapToInt(ProjectTask::getPoints).sum();

        int totalSprints = sprints.size();
        long activeSprints = sprints.stream().filter(s -> s.getStatus() == SprintStatus.DOING).count();
        long completedSprints = sprints.stream().filter(s -> s.getStatus() == SprintStatus.DONE).count();

        int sprintCompletionRate = totalSprints == 0
                ? 0
                : (int) Math.round((completedSprints * 100.0) / totalSprints);
        int taskCompletionRate = totalTasks == 0
                ? 0
                : (int) Math.round((doneCount * 100.0) / totalTasks);

        // +1 accounts for the project creator who may not appear in the member table
        int membersCount = acceptedMembers.size() + (isCreator ? 0 : 0);
        boolean creatorInMembers = acceptedMembers.stream()
                .anyMatch(m -> m.getUserId().equals(project.getCreatorId()));
        if (!creatorInMembers) {
            membersCount += 1;
        }

        return new ProjectMetricsResponse(
                currentMonth,
                totalTasks,
                (int) todoCount,
                (int) doingCount,
                (int) doneCount,
                tasksThisMonth,
                (int) doneThisMonth,
                totalPoints,
                completedPoints,
                velocityThisMonth,
                totalSprints,
                (int) activeSprints,
                (int) completedSprints,
                sprintCompletionRate,
                taskCompletionRate,
                membersCount);
    }

    private Project findAndAuthorize(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        if (!project.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the project creator can perform this action");
        }
        return project;
    }

    static ProjectResponse toResponse(Project p) {
        return new ProjectResponse(p.getId(), p.getName(), p.getDescription(),
                p.getCreatorId(), p.getDateStart(), p.getStatus(), p.getType());
    }
}
