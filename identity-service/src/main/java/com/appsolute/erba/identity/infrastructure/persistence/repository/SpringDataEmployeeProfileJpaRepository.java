package com.appsolute.erba.identity.infrastructure.persistence.repository;

import com.appsolute.erba.identity.infrastructure.persistence.entity.EmployeeProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataEmployeeProfileJpaRepository extends JpaRepository<EmployeeProfileEntity, UUID> {

    Optional<EmployeeProfileEntity> findByIdentityUserId(UUID identityUserId);
}