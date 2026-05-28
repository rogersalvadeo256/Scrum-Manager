package com.scrummanager.domain.model;

import com.scrummanager.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbr_cod")
    private Long id;

    @Column(name = "mbr_invited_by_id", nullable = false)
    private Long invitedById;

    @Column(name = "mbr_user_id", nullable = false)
    private Long userId;

    @Column(name = "mbr_project_id", nullable = false)
    private Long projectId;

    @Column(name = "mbr_invite_sent_date")
    private LocalDate inviteSentDate;

    @Column(name = "mbr_invite_answered_date")
    private LocalDate inviteAnsweredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "mbr_invite_status", nullable = false)
    private RequestStatus inviteStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "mbr_status")
    private RequestStatus memberStatus;

    @Column(name = "mbr_function")
    private String function;

    @Column(name = "mbr_permissions")
    private String permissions;

    @Column(name = "mbr_scrum_master", nullable = false)
    private boolean scrumMaster;
}
