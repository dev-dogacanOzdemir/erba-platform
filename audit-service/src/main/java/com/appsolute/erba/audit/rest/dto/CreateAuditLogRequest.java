package com.appsolute.erba.audit.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateAuditLogRequest(
        @NotBlank
        @Size(max = 100)
        String sourceService,

        UUID actorUserId,

        @NotBlank
        @Size(max = 100)
        String action,

        @NotBlank
        @Size(max = 100)
        String resourceType,

        UUID resourceId,

        @Size(max = 1000)
        String description
) {
}