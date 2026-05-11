package com.appsolute.erba.identity.infrastructure.client.dto;

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