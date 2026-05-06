package com.appsolute.erba.auth.infrastructure.persistence.repository;

import com.appsolute.erba.auth.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataRefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {

    Optional<RefreshTokenJpaEntity> findByTokenHash(String tokenHash);

    @Query("""
            select rt
            from RefreshTokenJpaEntity rt
            where rt.userId = :userId
              and rt.revokedAt is null
              and rt.expiresAt > :now
            """)
    List<RefreshTokenJpaEntity> findActiveTokensByUserId(
            @Param("userId") UUID userId,
            @Param("now") LocalDateTime now
    );

    @Modifying
    @Query("""
            update RefreshTokenJpaEntity rt
            set rt.revokedAt = :now
            where rt.userId = :userId
              and rt.revokedAt is null
              and rt.expiresAt > :now
            """)
    void revokeAllActiveTokensByUserId(
            @Param("userId") UUID userId,
            @Param("now") LocalDateTime now
    );
}