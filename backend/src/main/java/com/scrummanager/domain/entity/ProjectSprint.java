package com.scrummanager.domain.entity;

import com.scrummanager.domain.enums.SprintStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_sprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sprint_cod")
    private Long id;

    @Column(name = "sprint_title", nullable = false)
    private String title;

    @Column(name = "sprint_text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "proj_sprint_cod", nullable = false)
    private Long projectId;

    @Column(name = "sprint_points")
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(name = "sprint_status", nullable = false)
    private SprintStatus status;
}
