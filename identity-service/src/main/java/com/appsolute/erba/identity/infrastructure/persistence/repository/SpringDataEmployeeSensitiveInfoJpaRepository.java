package com.appsolute.erba.identity.infrastructure.persistence.repository;

import com.appsolute.erba.identity.infrastructure.persistence.entity.EmployeeSensitiveInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataEmployeeSensitiveInfoJpaRepository extends JpaRepository<EmployeeSensitiveInfoEntity, UUID> {

    Optional<EmployeeSensitiveInfoEntity> findByIdentityUserId(UUID identityUserId);
}