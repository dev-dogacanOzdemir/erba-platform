package com.appsolute.erba.audit.application.dto;

import java.util.UUID;

public record SearchAuditLogsCommand(
        String sourceService,
        UUID actorUserId,
        String action,
        String resourceType,
        UUID resourceId,
        int page,
        int size
) {
}