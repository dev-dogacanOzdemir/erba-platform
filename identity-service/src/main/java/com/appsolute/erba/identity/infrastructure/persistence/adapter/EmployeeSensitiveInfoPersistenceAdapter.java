package com.appsolute.erba.identity.infrastructure.persistence.adapter;

import com.appsolute.erba.identity.domain.model.EmployeeSensitiveInfo;
import com.appsolute.erba.identity.domain.port.EmployeeSensitiveInfoRepository;
import com.appsolute.erba.identity.infrastructure.persistence.entity.EmployeeSensitiveInfoEntity;
import com.appsolute.erba.identity.infrastructure.persistence.repository.SpringDataEmployeeSensitiveInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmployeeSensitiveInfoPersistenceAdapter implements EmployeeSensitiveInfoRepository {

    private final SpringDataEmployeeSensitiveInfoJpaRepository jpaRepository;

    @Override
    public EmployeeSensitiveInfo save(EmployeeSensitiveInfo info) {
        EmployeeSensitiveInfoEntity entity = toEntity(info);
        EmployeeSensitiveInfoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<EmployeeSensitiveInfo> findByIdentityUserId(UUID identityUserId) {
        return jpaRepository.findByIdentityUserId(identityUserId)
                .map(this::toDomain);
    }

    private EmployeeSensitiveInfoEntity toEntity(EmployeeSensitiveInfo info) {
        return EmployeeSensitiveInfoEntity.builder()
                .id(info.getId())
                .identityUserId(info.getIdentityUserId())
                .nationalId(info.getNationalId())
                .sgkNumber(info.getSgkNumber())
                .createdAt(info.getCreatedAt())
                .updatedAt(info.getUpdatedAt())
                .build();
    }

    private EmployeeSensitiveInfo toDomain(EmployeeSensitiveInfoEntity entity) {
        return EmployeeSensitiveInfo.restore(
                entity.getId(),
                entity.getIdentityUserId(),
                entity.getNationalId(),
                entity.getSgkNumber(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}