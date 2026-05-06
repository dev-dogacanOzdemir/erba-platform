package com.appsolute.erba.auth.infrastructure.persistence.adapter;

import com.appsolute.erba.auth.domain.model.RefreshToken;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.appsolute.erba.auth.infrastructure.persistence.repository.SpringDataRefreshTokenJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenPersistenceAdapter implements RefreshTokenRepository {

    private final SpringDataRefreshTokenJpaRepository repository;

    public RefreshTokenPersistenceAdapter(SpringDataRefreshTokenJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenJpaEntity savedEntity = repository.save(toEntity(refreshToken));
        return toDomain(savedEntity);
    }

    @Override
    public Optional<RefreshToken> findByTokenHash(String tokenHash) {
        return repository.findByTokenHash(tokenHash)
                .map(this::toDomain);
    }

    @Override
    public List<RefreshToken> findActiveTokensByUserId(UUID userId) {
        return repository.findActiveTokensByUserId(userId, LocalDateTime.now())
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void revokeAllActiveTokensByUserId(UUID userId) {
        repository.revokeAllActiveTokensByUserId(userId, LocalDateTime.now());
    }

    private RefreshTokenJpaEntity toEntity(RefreshToken domain) {
        RefreshTokenJpaEntity entity = new RefreshTokenJpaEntity();

        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setTokenHash(domain.getTokenHash());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setRevokedAt(domain.getRevokedAt());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }

    private RefreshToken toDomain(RefreshTokenJpaEntity entity) {
        return new RefreshToken(
                entity.getId(),
                entity.getUserId(),
                entity.getTokenHash(),
                entity.getExpiresAt(),
                entity.getRevokedAt(),
                entity.getCreatedAt()
        );
    }
}