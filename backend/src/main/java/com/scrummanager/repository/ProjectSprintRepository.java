package com.scrummanager.repository;

import com.scrummanager.domain.entity.ProjectSprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectSprintRepository extends JpaRepository<ProjectSprint, Long> {
    List<ProjectSprint> findByProjectId(Long projectId);
}
