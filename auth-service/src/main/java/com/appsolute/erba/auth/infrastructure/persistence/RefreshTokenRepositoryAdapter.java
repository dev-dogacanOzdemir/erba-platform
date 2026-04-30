package com.appsolute.erba.auth.infrastructure.persistence;

import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final SpringDataRefreshTokenJpaRepository repository;

    public RefreshTokenRepositoryAdapter(SpringDataRefreshTokenJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void revokeActiveTokensByUserId(UserId userId) {
        List<RefreshTokenJpaEntity> activeTokens =
                repository.findByUserIdAndRevokedAtIsNull(userId.getValue());

        LocalDateTime now = LocalDateTime.now();

        activeTokens.forEach(token -> token.setRevokedAt(now));

        repository.saveAll(activeTokens);
    }

    @Override
    public void save(UserId userId, String tokenHash, LocalDateTime expiresAt) {
        RefreshTokenJpaEntity entity = new RefreshTokenJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setUserId(userId.getValue());
        entity.setTokenHash(tokenHash);
        entity.setExpiresAt(expiresAt);
        entity.setCreatedAt(LocalDateTime.now());

        repository.save(entity);
    }
}