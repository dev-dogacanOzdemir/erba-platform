package com.appsolute.erba.identity.infrastructure.persistence.repository;

import com.appsolute.erba.identity.infrastructure.persistence.entity.IdentityUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataIdentityUserJpaRepository extends JpaRepository<IdentityUserEntity, UUID> {

    Optional<IdentityUserEntity> findByAuthUserId(UUID authUserId);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByAuthUserId(UUID authUserId);
}