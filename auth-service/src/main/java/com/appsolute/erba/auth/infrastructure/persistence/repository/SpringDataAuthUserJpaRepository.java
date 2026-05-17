package com.appsolute.erba.auth.infrastructure.persistence.repository;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import com.appsolute.erba.auth.infrastructure.persistence.entity.AuthUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataAuthUserJpaRepository extends JpaRepository<AuthUserJpaEntity, UUID> {

    Optional<AuthUserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<AuthUserJpaEntity> findByRoleAndStatusAndDeletedAtIsNull(
            AuthRole role,
            AuthUserStatus status
    );
}