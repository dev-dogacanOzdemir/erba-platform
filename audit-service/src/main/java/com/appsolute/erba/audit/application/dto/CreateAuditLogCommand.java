package com.appsolute.erba.audit.application.dto;

import java.util.UUID;

public record CreateAuditLogCommand(
        String sourceService,
        UUID actorUserId,
        String action,
        String resourceType,
        UUID resourceId,
        String description
) {
}