package com.appsolute.erba.audit.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListAuditLogResult(
        UUID id,
        String sourceService,
        UUID actorUserId,
        String action,
        String resourceType,
        UUID resourceId,
        String description,
        LocalDateTime createdAt
) {
}