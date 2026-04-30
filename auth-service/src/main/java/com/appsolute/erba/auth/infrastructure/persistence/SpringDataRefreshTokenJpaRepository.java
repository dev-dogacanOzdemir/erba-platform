package com.appsolute.erba.auth.infrastructure.persistence;

import com.appsolute.erba.auth.domain.valueobject.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataRefreshTokenJpaRepository
        extends JpaRepository<RefreshTokenJpaEntity, UUID> {

    List<RefreshTokenJpaEntity> findByUserIdAndRevokedAtIsNull(UUID userId);
}