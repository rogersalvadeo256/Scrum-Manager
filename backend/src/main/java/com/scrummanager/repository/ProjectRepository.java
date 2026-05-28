package com.scrummanager.repository;

import com.scrummanager.domain.entity.Project;
import com.scrummanager.domain.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCreatorIdAndStatusNot(Long creatorId, ProjectStatus status);
    List<Project> findByIdInAndStatusNot(List<Long> ids, ProjectStatus status);
}
