package com.scrummanager.domain.model;

import com.scrummanager.domain.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_cod")
    private Long id;

    @Column(name = "proj_name", nullable = false)
    private String name;

    @Column(name = "proj_description")
    private String description;

    @Column(name = "proj_creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "proj_date_start", nullable = false)
    private LocalDate dateStart;

    @Enumerated(EnumType.STRING)
    @Column(name = "proj_status", nullable = false)
    private ProjectStatus status;

    @Column(name = "proj_type")
    private String type;
}
