package com.appsolute.erba.identity.infrastructure.persistence.adapter;

import com.appsolute.erba.identity.domain.model.EmployeeProfile;
import com.appsolute.erba.identity.domain.port.EmployeeProfileRepository;
import com.appsolute.erba.identity.infrastructure.persistence.entity.EmployeeProfileEntity;
import com.appsolute.erba.identity.infrastructure.persistence.repository.SpringDataEmployeeProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmployeeProfilePersistenceAdapter implements EmployeeProfileRepository {

    private final SpringDataEmployeeProfileJpaRepository jpaRepository;

    @Override
    public EmployeeProfile save(EmployeeProfile profile) {
        EmployeeProfileEntity entity = toEntity(profile);
        EmployeeProfileEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<EmployeeProfile> findByIdentityUserId(UUID identityUserId) {
        return jpaRepository.findByIdentityUserId(identityUserId)
                .map(this::toDomain);
    }

    private EmployeeProfileEntity toEntity(EmployeeProfile profile) {
        return EmployeeProfileEntity.builder()
                .id(profile.getId())
                .identityUserId(profile.getIdentityUserId())
                .employeeNumber(profile.getEmployeeNumber())
                .department(profile.getDepartment())
                .position(profile.getPosition())
                .employmentType(profile.getEmploymentType())
                .hireDate(profile.getHireDate())
                .terminationDate(profile.getTerminationDate())
                .birthDate(profile.getBirthDate())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    private EmployeeProfile toDomain(EmployeeProfileEntity entity) {
        return EmployeeProfile.restore(
                entity.getId(),
                entity.getIdentityUserId(),
                entity.getEmployeeNumber(),
                entity.getDepartment(),
                entity.getPosition(),
                entity.getEmploymentType(),
                entity.getHireDate(),
                entity.getTerminationDate(),
                entity.getBirthDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}