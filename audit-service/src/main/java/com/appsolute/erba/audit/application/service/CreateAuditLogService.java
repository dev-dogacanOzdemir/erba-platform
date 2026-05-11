package com.appsolute.erba.audit.application.service;

import com.appsolute.erba.audit.application.dto.CreateAuditLogCommand;
import com.appsolute.erba.audit.domain.model.AuditLog;
import com.appsolute.erba.audit.domain.port.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAuditLogService {

    private final AuditLogRepository auditLogRepository;

    public CreateAuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void create(CreateAuditLogCommand command) {
        AuditLog auditLog = AuditLog.create(
                command.sourceService(),
                command.actorUserId(),
                command.action(),
                command.resourceType(),
                command.resourceId(),
                command.description()
        );

        auditLogRepository.save(auditLog);
    }
}