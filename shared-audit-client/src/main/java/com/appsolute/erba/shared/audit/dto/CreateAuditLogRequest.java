package com.appsolute.erba.shared.audit.dto;

import java.util.UUID;

public record CreateAuditLogRequest(
        String sourceService,
        UUID actorUserId,
        String action,
        String resourceType,
        UUID resourceId,
        String description
) {
}