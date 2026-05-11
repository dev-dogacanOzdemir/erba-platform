package com.appsolute.erba.audit.application.service;

import com.appsolute.erba.audit.application.dto.ListAuditLogResult;
import com.appsolute.erba.audit.application.dto.PagedAuditLogResult;
import com.appsolute.erba.audit.application.dto.SearchAuditLogsCommand;
import com.appsolute.erba.audit.domain.model.AuditLog;
import com.appsolute.erba.audit.domain.port.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListAuditLogsService {

    private static final int MAX_PAGE_SIZE = 100;

    private final AuditLogRepository auditLogRepository;

    public ListAuditLogsService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(readOnly = true)
    public PagedAuditLogResult search(SearchAuditLogsCommand command) {
        int page = Math.max(command.page(), 0);
        int size = Math.min(Math.max(command.size(), 1), MAX_PAGE_SIZE);

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<AuditLog> resultPage = auditLogRepository.search(
                command.sourceService(),
                command.actorUserId(),
                command.action(),
                command.resourceType(),
                command.resourceId(),
                pageRequest
        );

        return new PagedAuditLogResult(
                resultPage.getContent()
                        .stream()
                        .map(this::toResult)
                        .toList(),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages(),
                resultPage.isFirst(),
                resultPage.isLast()
        );
    }

    private ListAuditLogResult toResult(AuditLog auditLog) {
        return new ListAuditLogResult(
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
}