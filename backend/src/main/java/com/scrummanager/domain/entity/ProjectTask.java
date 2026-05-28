package com.scrummanager.domain.entity;

import com.scrummanager.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_cod")
    private Long id;

    @Column(name = "task_title", nullable = false)
    private String title;

    @Column(name = "task_text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "task_points")
    private int points;

    @Column(name = "task_creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "task_executor_id")
    private Long executorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus status;

    @Column(name = "proj_cod", nullable = false)
    private Long projectId;

    @Column(name = "task_date_start")
    private LocalDate dateStart;
}
