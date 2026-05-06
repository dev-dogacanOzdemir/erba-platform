package com.appsolute.erba.auth.infrastructure.persistence.repository;

import com.appsolute.erba.auth.infrastructure.persistence.entity.PasswordResetTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenJpaEntity, UUID> {

    Optional<PasswordResetTokenJpaEntity> findByTokenHash(String tokenHash);

    @Modifying
    @Query("""
            update PasswordResetTokenJpaEntity prt
            set prt.usedAt = :now
            where prt.userId = :userId
              and prt.usedAt is null
              and prt.expiresAt > :now
            """)
    void revokeAllUsableTokensByUserId(
            @Param("userId") UUID userId,
            @Param("now") LocalDateTime now
    );
}