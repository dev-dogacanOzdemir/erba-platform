package com.appsolute.erba.audit.infrastructure.persistence.adapter;

import com.appsolute.erba.audit.domain.model.AuditLog;
import com.appsolute.erba.audit.domain.port.AuditLogRepository;
import com.appsolute.erba.audit.infrastructure.persistence.entity.AuditLogJpaEntity;
import com.appsolute.erba.audit.infrastructure.persistence.repository.SpringDataAuditLogJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuditLogPersistenceAdapter implements AuditLogRepository {

    private final SpringDataAuditLogJpaRepository jpaRepository;

    public AuditLogPersistenceAdapter(SpringDataAuditLogJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AuditLog save(AuditLog auditLog) {
        AuditLogJpaEntity savedEntity = jpaRepository.save(toEntity(auditLog));
        return toDomain(savedEntity);
    }

    @Override
    public Page<AuditLog> search(
            String sourceService,
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            Pageable pageable
    ) {
        return jpaRepository.search(
                        sourceService,
                        actorUserId,
                        action,
                        resourceType,
                        resourceId,
                        pageable
                )
                .map(this::toDomain);
    }

    private AuditLogJpaEntity toEntity(AuditLog auditLog) {
        return new AuditLogJpaEntity(
                auditLog.getId(),
                auditLog.getSourceService(),
                auditLog.getActorUserId(),
                auditLog.getAction(),
                auditLog.getResourceType(),
                auditLog.getResourceId(),
                auditLog.getDescription(),
                auditLog.getCreatedAt()
        );
    }

    private AuditLog toDomain(AuditLogJpaEntity entity) {
        return AuditLog.restore(
                entity.getId(),
                entity.getSourceService(),
                entity.getActorUserId(),
                entity.getAction(),
                entity.getResourceType(),
                entity.getResourceId(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}