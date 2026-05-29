package com.scrummanager.business.contract;

import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.Project;
import com.scrummanager.domain.model.ProjectMember;

import java.util.List;

public interface ProjectBusinessContract {
    Project create(String name, String description, String type, Long creatorId);
    List<Project> getByCreator(Long userId);
    List<Project> getByMember(Long userId);
    Project update(Long projectId, String name, String description, String type, Long userId);
    Project delete(Long projectId, Long userId);
    ProjectMember inviteMember(Long projectId, Long targetUserId, Long invitedById);
    ProjectMember answerInvite(Long memberId, RequestStatus answer, Long userId);
    List<ProjectMember> getPendingInvites(Long userId);
    ProjectMetricsData computeMetrics(Long projectId, Long userId);
    Project findAndAuthorize(Long projectId, Long userId);
}
