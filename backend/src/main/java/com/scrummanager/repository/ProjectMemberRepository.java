package com.scrummanager.repository;

import com.scrummanager.domain.model.ProjectMember;
import com.scrummanager.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByUserIdAndInviteStatus(Long userId, RequestStatus status);
    List<ProjectMember> findByProjectIdAndInviteStatus(Long projectId, RequestStatus status);
    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserIdAndInviteStatus(Long projectId, Long userId, RequestStatus status);

    @Query("""
            select pm from ProjectMember pm
            where pm.inviteStatus = :status
              and pm.inviteSentDate is not null
              and pm.inviteSentDate <= :cutoff
            """)
    List<ProjectMember> findPendingInvitesSentBefore(@Param("status") RequestStatus status,
                                                     @Param("cutoff") LocalDate cutoff);
}
