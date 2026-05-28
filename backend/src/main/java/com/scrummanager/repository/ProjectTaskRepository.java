package com.scrummanager.repository;

import com.scrummanager.domain.entity.ProjectTask;
import com.scrummanager.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findByProjectId(Long projectId);
    List<ProjectTask> findByProjectIdAndStatus(Long projectId, TaskStatus status);
    List<ProjectTask> findByExecutorId(Long executorId);
}
