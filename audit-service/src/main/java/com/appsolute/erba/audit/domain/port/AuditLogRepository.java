package com.appsolute.erba.audit.domain.port;

import com.appsolute.erba.audit.domain.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuditLogRepository {

    AuditLog save(AuditLog auditLog);

    Page<AuditLog> search(
            String sourceService,
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            Pageable pageable
    );
}