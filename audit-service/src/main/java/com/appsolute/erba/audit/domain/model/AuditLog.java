package com.appsolute.erba.audit.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditLog {

    private UUID id;
    private String sourceService;
    private UUID actorUserId;
    private String action;
    private String resourceType;
    private UUID resourceId;
    private String description;
    private LocalDateTime createdAt;

    private AuditLog(
            UUID id,
            String sourceService,
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            String description,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.sourceService = sourceService;
        this.actorUserId = actorUserId;
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static AuditLog create(
            String sourceService,
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            String description
    ) {
        return new AuditLog(
                UUID.randomUUID(),
                sourceService,
                actorUserId,
                action,
                resourceType,
                resourceId,
                description,
                LocalDateTime.now()
        );
    }

    public static AuditLog restore(
            UUID id,
            String sourceService,
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            String description,
            LocalDateTime createdAt
    ) {
        return new AuditLog(
                id,
                sourceService,
                actorUserId,
                action,
                resourceType,
                resourceId,
                description,
                createdAt
        );
    }

    public UUID getId() { return id; }
    public String getSourceService() { return sourceService; }
    public UUID getActorUserId() { return actorUserId; }
    public String getAction() { return action; }
    public String getResourceType() { return resourceType; }
    public UUID getResourceId() { return resourceId; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}