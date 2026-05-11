package com.appsolute.erba.audit.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListAuditLogResponse(
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