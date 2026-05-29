package com.scrummanager.business;

import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.enums.SprintStatus;
import com.scrummanager.domain.enums.TaskStatus;
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
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProjectBusiness {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;
    private final ProjectTaskRepository taskRepository;
    private final ProjectSprintRepository sprintRepository;

    @Transactional
    public Project create(String name, String description, String type, Long creatorId) {
        Project project = Project.builder()
                .name(name)
                .description(description)
                .type(type)
                .creatorId(creatorId)
                .dateStart(LocalDate.now())
                .status(ProjectStatus.IN_PROGRESS)
                .build();
        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public List<Project> getByCreator(Long userId) {
        return projectRepository.findByCreatorIdAndStatusNot(userId, ProjectStatus.DELETED);
    }

    @Transactional(readOnly = true)
    public List<Project> getByMember(Long userId) {
        List<Long> projectIds = memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ACCEPTED)
                .stream()
                .map(ProjectMember::getProjectId)
                .distinct()
                .toList();
        if (projectIds.isEmpty()) {
            return List.of();
        }
        return projectRepository.findByIdInAndStatusNot(projectIds, ProjectStatus.DELETED);
    }

    @Transactional
    public Project update(Long projectId, String name, String description, String type, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setName(name);
        project.setDescription(description);
        project.setType(type);
        return projectRepository.save(project);
    }

    @Transactional
    public Project delete(Long projectId, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setStatus(ProjectStatus.DELETED);
        return projectRepository.save(project);
    }

    @Transactional
    public ProjectMember inviteMember(Long projectId, Long targetUserId, Long invitedById) {
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
        return memberRepository.save(member);
    }

    @Transactional
    public ProjectMember answerInvite(Long memberId, RequestStatus answer, Long userId) {
        ProjectMember member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invite not found"));
        if (!member.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not your invitation");
        }
        member.setInviteStatus(answer);
        member.setInviteAnsweredDate(LocalDate.now());
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<ProjectMember> getPendingInvites(Long userId) {
        return memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ON_HOLD);
    }

    @Transactional(readOnly = true)
    public ProjectMetricsData computeMetrics(Long projectId, Long userId) {
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
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " " + now.getYear();

        List<ProjectTask> tasks = taskRepository.findByProjectId(projectId);
        List<ProjectSprint> sprints = sprintRepository.findByProjectId(projectId);
        List<ProjectMember> acceptedMembers = memberRepository.findByProjectIdAndInviteStatus(
                projectId, RequestStatus.ACCEPTED);

        int totalTasks = tasks.size();
        long todoCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.TO_DO).count();
        long doingCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DOING).count();
        long doneCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();

        List<ProjectTask> tasksThisMonth = tasks.stream()
                .filter(t -> t.getDateStart() != null
                        && !t.getDateStart().isBefore(monthStart)
                        && !t.getDateStart().isAfter(monthEnd))
                .toList();
        int tasksThisMonthCount = tasksThisMonth.size();
        long doneThisMonth = tasksThisMonth.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        int velocityThisMonth = tasksThisMonth.stream()
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
                ? 0 : (int) Math.round((completedSprints * 100.0) / totalSprints);
        int taskCompletionRate = totalTasks == 0
                ? 0 : (int) Math.round((doneCount * 100.0) / totalTasks);

        boolean creatorInMembers = acceptedMembers.stream()
                .anyMatch(m -> m.getUserId().equals(project.getCreatorId()));
        int membersCount = acceptedMembers.size() + (creatorInMembers ? 0 : 1);

        return new ProjectMetricsData(currentMonth, totalTasks, (int) todoCount, (int) doingCount, (int) doneCount,
                tasksThisMonthCount, (int) doneThisMonth, totalPoints, completedPoints, velocityThisMonth,
                totalSprints, (int) activeSprints, (int) completedSprints,
                sprintCompletionRate, taskCompletionRate, membersCount);
    }

    public Project findAndAuthorize(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        if (!project.getCreatorId().equals(userId)) {
            throw new AccessDeniedException("Only the project creator can perform this action");
        }
        return project;
    }

    public record ProjectMetricsData(
            String currentMonth, int totalTasks, int todoCount, int doingCount, int doneCount,
            int tasksThisMonth, int doneThisMonth, int totalPoints, int completedPoints, int velocityThisMonth,
            int totalSprints, int activeSprints, int completedSprints,
            int sprintCompletionRate, int taskCompletionRate, int membersCount) {}
}
