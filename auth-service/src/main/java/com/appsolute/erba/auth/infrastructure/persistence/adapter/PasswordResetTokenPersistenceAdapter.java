package com.appsolute.erba.auth.infrastructure.persistence.adapter;

import com.appsolute.erba.auth.domain.model.PasswordResetToken;
import com.appsolute.erba.auth.domain.port.PasswordResetTokenRepository;
import com.appsolute.erba.auth.infrastructure.persistence.entity.PasswordResetTokenJpaEntity;
import com.appsolute.erba.auth.infrastructure.persistence.repository.SpringDataPasswordResetTokenJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class PasswordResetTokenPersistenceAdapter implements PasswordResetTokenRepository {

    private final SpringDataPasswordResetTokenJpaRepository repository;

    public PasswordResetTokenPersistenceAdapter(SpringDataPasswordResetTokenJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        PasswordResetTokenJpaEntity savedEntity = repository.save(toEntity(token));
        return toDomain(savedEntity);
    }

    @Override
    public Optional<PasswordResetToken> findByTokenHash(String tokenHash) {
        return repository.findByTokenHash(tokenHash)
                .map(this::toDomain);
    }

    @Override
    @Transactional
    public void revokeAllUsableTokensByUserId(UUID userId) {
        repository.revokeAllUsableTokensByUserId(userId, LocalDateTime.now());
    }

    private PasswordResetTokenJpaEntity toEntity(PasswordResetToken domain) {
        PasswordResetTokenJpaEntity entity = new PasswordResetTokenJpaEntity();

        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setTokenHash(domain.getTokenHash());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setUsedAt(domain.getUsedAt());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }

    private PasswordResetToken toDomain(PasswordResetTokenJpaEntity entity) {
        return new PasswordResetToken(
                entity.getId(),
                entity.getUserId(),
                entity.getTokenHash(),
                entity.getExpiresAt(),
                entity.getUsedAt(),
                entity.getCreatedAt()
        );
    }
}