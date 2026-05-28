package com.scrummanager.service;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.enums.ProjectStatus;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.dto.request.ProjectRequest;
import com.scrummanager.dto.response.ProjectResponse;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository memberRepository;

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
        return toResponse(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects(Long userId) {
        return projectRepository.findByCreatorIdAndStatusNot(userId, ProjectStatus.DELETED)
                .stream().map(ProjectService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsAsMember(Long userId) {
        return memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ACCEPTED)
                .stream()
                .map(m -> projectRepository.findById(m.getProjectId()).orElseThrow())
                .filter(p -> p.getStatus() != ProjectStatus.DELETED)
                .map(ProjectService::toResponse)
                .toList();
    }

    @Transactional
    public ProjectResponse update(Long projectId, ProjectRequest req, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setName(req.name());
        project.setDescription(req.description());
        project.setType(req.type());
        return toResponse(projectRepository.save(project));
    }

    @Transactional
    public void delete(Long projectId, Long userId) {
        Project project = findAndAuthorize(projectId, userId);
        project.setStatus(ProjectStatus.DELETED);
        projectRepository.save(project);
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
    }

    @Transactional(readOnly = true)
    public List<ProjectMember> getPendingInvites(Long userId) {
        return memberRepository.findByUserIdAndInviteStatus(userId, RequestStatus.ON_HOLD);
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
