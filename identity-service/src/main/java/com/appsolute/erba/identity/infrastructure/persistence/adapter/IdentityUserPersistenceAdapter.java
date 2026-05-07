package com.appsolute.erba.identity.infrastructure.persistence.adapter;

import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.infrastructure.persistence.entity.IdentityUserEntity;
import com.appsolute.erba.identity.infrastructure.persistence.repository.SpringDataIdentityUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class IdentityUserPersistenceAdapter implements IdentityUserRepository {

    private final SpringDataIdentityUserJpaRepository jpaRepository;

    @Override
    public IdentityUser save(IdentityUser user) {
        IdentityUserEntity entity = toEntity(user);
        IdentityUserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<IdentityUser> findById(UUID id) {
        return jpaRepository.findById(id)
                .filter(entity -> entity.getDeletedAt() == null)
                .map(this::toDomain);
    }

    @Override
    public Optional<IdentityUser> findByAuthUserId(UUID authUserId) {
        return jpaRepository.findByAuthUserId(authUserId)
                .filter(entity -> entity.getDeletedAt() == null)
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmailAndDeletedAtIsNull(email);
    }

    @Override
    public boolean existsByAuthUserId(UUID authUserId) {
        return jpaRepository.existsByAuthUserId(authUserId);
    }

    @Override
    public List<IdentityUser> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private IdentityUserEntity toEntity(IdentityUser user) {
        return IdentityUserEntity.builder()
                .id(user.getId())
                .authUserId(user.getAuthUserId())
                .userType(user.getUserType())
                .status(user.getStatus())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .profilePhotoId(user.getProfilePhotoId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    private IdentityUser toDomain(IdentityUserEntity entity) {
        return IdentityUser.restore(
                entity.getId(),
                entity.getAuthUserId(),
                entity.getUserType(),
                entity.getStatus(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getProfilePhotoId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}