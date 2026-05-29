package com.scrummanager.repository;

import com.scrummanager.domain.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findByOwnerIdAndActiveTrue(Long ownerId);

    Optional<ApiKey> findByKeyHashAndActiveTrue(String keyHash);
}
