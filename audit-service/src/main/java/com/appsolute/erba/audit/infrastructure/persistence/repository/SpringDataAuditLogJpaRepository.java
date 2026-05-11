package com.appsolute.erba.audit.infrastructure.persistence.repository;

import com.appsolute.erba.audit.infrastructure.persistence.entity.AuditLogJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SpringDataAuditLogJpaRepository
        extends JpaRepository<AuditLogJpaEntity, UUID> {

    @Query("""
            select auditLog
            from AuditLogJpaEntity auditLog
            where (:sourceService is null or auditLog.sourceService = :sourceService)
              and (:actorUserId is null or auditLog.actorUserId = :actorUserId)
              and (:action is null or auditLog.action = :action)
              and (:resourceType is null or auditLog.resourceType = :resourceType)
              and (:resourceId is null or auditLog.resourceId = :resourceId)
            """)
    Page<AuditLogJpaEntity> search(
            @Param("sourceService") String sourceService,
            @Param("actorUserId") UUID actorUserId,
            @Param("action") String action,
            @Param("resourceType") String resourceType,
            @Param("resourceId") UUID resourceId,
            Pageable pageable
    );
}