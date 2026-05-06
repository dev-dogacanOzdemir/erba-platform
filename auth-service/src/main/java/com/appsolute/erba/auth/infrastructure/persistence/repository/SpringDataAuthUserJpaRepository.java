package com.appsolute.erba.auth.infrastructure.persistence.repository;

import com.appsolute.erba.auth.infrastructure.persistence.entity.AuthUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAuthUserJpaRepository extends JpaRepository<AuthUserJpaEntity, UUID> {

    Optional<AuthUserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}