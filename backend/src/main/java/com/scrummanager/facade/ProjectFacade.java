package com.scrummanager.facade;

import com.scrummanager.business.contract.ProjectBusinessContract;
import com.scrummanager.business.contract.ProjectMetricsData;
import com.scrummanager.domain.dto.request.ProjectRequest;
import com.scrummanager.domain.dto.response.ProjectMetricsResponse;
import com.scrummanager.domain.dto.response.ProjectResponse;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Project;
import com.scrummanager.domain.model.ProjectMember;
import com.scrummanager.facade.contract.ProjectFacadeContract;
import com.scrummanager.service.contract.CacheInvalidationContract;
import com.scrummanager.service.contract.DomainEventPublisherContract;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectFacade implements ProjectFacadeContract {

    private final ProjectBusinessContract projectBusiness;
    private final CacheInvalidationContract cacheInvalidationService;
    private final DomainEventPublisherContract domainEventPublisher;

    public ProjectResponse create(ProjectRequest req, Long userId) {
        Project project = projectBusiness.create(req.name(), req.description(), req.type(), userId);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish("project.created", userId, "project", project.getId(),
                Map.of("name", project.getName()));
        return toResponse(project);
    }

    @Cacheable(cacheNames = "project-mine", key = "#userId")
    public List<ProjectResponse> getMyProjects(Long userId) {
        return projectBusiness.getByCreator(userId).stream().map(ProjectFacade::toResponse).toList();
    }

    @Cacheable(cacheNames = "project-member", key = "#userId")
    public List<ProjectResponse> getProjectsAsMember(Long userId) {
        return projectBusiness.getByMember(userId).stream().map(ProjectFacade::toResponse).toList();
    }

    public ProjectResponse update(Long id, ProjectRequest req, Long userId) {
        Project project = projectBusiness.update(id, req.name(), req.description(), req.type(), userId);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish("project.updated", userId, "project", project.getId(),
                Map.of("name", project.getName()));
        return toResponse(project);
    }

    public void delete(Long id, Long userId) {
        Project project = projectBusiness.delete(id, userId);
        cacheInvalidationService.evictProjectCaches();
        cacheInvalidationService.evictTaskCaches(id);
        domainEventPublisher.publish("project.deleted", userId, "project", id,
                Map.of("status", project.getStatus().name()));
    }

    public void inviteMember(Long projectId, Long targetUserId, Long invitedById) {
        ProjectMember member = projectBusiness.inviteMember(projectId, targetUserId, invitedById);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish("project.member.invited", invitedById, "project-member", member.getId(),
                Map.of("projectId", projectId, "targetUserId", targetUserId));
    }

    public void answerInvite(Long memberId, RequestStatus answer, Long userId) {
        ProjectMember member = projectBusiness.answerInvite(memberId, answer, userId);
        cacheInvalidationService.evictProjectCaches();
        domainEventPublisher.publish("project.member.invite-answered", userId, "project-member", memberId,
                Map.of("answer", answer.name(), "projectId", member.getProjectId()));
    }

    @Cacheable(cacheNames = "project-invites", key = "#userId")
    public List<ProjectMember> getPendingInvites(Long userId) {
        return projectBusiness.getPendingInvites(userId);
    }

    public ProjectMetricsResponse getProjectMetrics(Long projectId, Long userId) {
        ProjectMetricsData d = projectBusiness.computeMetrics(projectId, userId);
        return new ProjectMetricsResponse(
                d.currentMonth(), d.totalTasks(), d.todoCount(), d.doingCount(), d.doneCount(),
                d.tasksThisMonth(), d.doneThisMonth(), d.totalPoints(), d.completedPoints(),
                d.velocityThisMonth(), d.totalSprints(), d.activeSprints(), d.completedSprints(),
                d.sprintCompletionRate(), d.taskCompletionRate(), d.membersCount());
    }

    static ProjectResponse toResponse(Project p) {
        return new ProjectResponse(p.getId(), p.getName(), p.getDescription(),
                p.getCreatorId(), p.getDateStart(), p.getStatus(), p.getType());
    }
}
