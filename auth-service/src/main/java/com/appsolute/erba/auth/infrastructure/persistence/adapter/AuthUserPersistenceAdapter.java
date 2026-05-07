package com.appsolute.erba.auth.infrastructure.persistence.adapter;

import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.infrastructure.persistence.entity.AuthUserJpaEntity;
import com.appsolute.erba.auth.infrastructure.persistence.repository.SpringDataAuthUserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthUserPersistenceAdapter implements AuthUserRepository {

    private final SpringDataAuthUserJpaRepository repository;

    public AuthUserPersistenceAdapter(SpringDataAuthUserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthUser save(AuthUser authUser) {
        AuthUserJpaEntity savedEntity = repository.save(toEntity(authUser));
        return toDomain(savedEntity);
    }

    @Override
    public Optional<AuthUser> findById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public List<AuthUser> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private AuthUserJpaEntity toEntity(AuthUser domain) {
        AuthUserJpaEntity entity = new AuthUserJpaEntity();

        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setRole(domain.getRole());
        entity.setStatus(domain.getStatus());
        entity.setFailedLoginCount(domain.getFailedLoginCount());
        entity.setLockedUntil(domain.getLockedUntil());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());

        return entity;
    }

    private AuthUser toDomain(AuthUserJpaEntity entity) {
        return new AuthUser(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getStatus(),
                entity.getFailedLoginCount(),
                entity.getLockedUntil(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}