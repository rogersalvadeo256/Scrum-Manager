package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.request.ProjectRequest;
import com.scrummanager.domain.dto.response.ProjectMetricsResponse;
import com.scrummanager.domain.dto.response.ProjectResponse;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.domain.model.ProjectMember;

import java.util.List;

public interface ProjectFacadeContract {
    ProjectResponse create(ProjectRequest req, Long userId);
    List<ProjectResponse> getMyProjects(Long userId);
    List<ProjectResponse> getProjectsAsMember(Long userId);
    ProjectResponse update(Long id, ProjectRequest req, Long userId);
    void delete(Long id, Long userId);
    void inviteMember(Long projectId, Long targetUserId, Long invitedById);
    void answerInvite(Long memberId, RequestStatus answer, Long userId);
    List<ProjectMember> getPendingInvites(Long userId);
    ProjectMetricsResponse getProjectMetrics(Long projectId, Long userId);
}
