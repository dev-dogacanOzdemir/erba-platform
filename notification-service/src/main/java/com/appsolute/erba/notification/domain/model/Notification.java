package com.appsolute.erba.notification.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private UUID id;
    private UUID recipientUserId;

    private String title;
    private String message;
    private String type;

    private String sourceService;
    private String sourceResourceType;
    private UUID sourceResourceId;

    private boolean read;

    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    private Notification(
            UUID id,
            UUID recipientUserId,
            String title,
            String message,
            String type,
            String sourceService,
            String sourceResourceType,
            UUID sourceResourceId,
            boolean read,
            LocalDateTime createdAt,
            LocalDateTime readAt
    ) {
        this.id = id;
        this.recipientUserId = recipientUserId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.sourceService = sourceService;
        this.sourceResourceType = sourceResourceType;
        this.sourceResourceId = sourceResourceId;
        this.read = read;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

    public static Notification create(
            UUID recipientUserId,
            String title,
            String message,
            String type,
            String sourceService,
            String sourceResourceType,
            UUID sourceResourceId
    ) {
        return new Notification(
                UUID.randomUUID(),
                recipientUserId,
                title,
                message,
                type,
                sourceService,
                sourceResourceType,
                sourceResourceId,
                false,
                LocalDateTime.now(),
                null
        );
    }

    public static Notification restore(
            UUID id,
            UUID recipientUserId,
            String title,
            String message,
            String type,
            String sourceService,
            String sourceResourceType,
            UUID sourceResourceId,
            boolean read,
            LocalDateTime createdAt,
            LocalDateTime readAt
    ) {
        return new Notification(
                id,
                recipientUserId,
                title,
                message,
                type,
                sourceService,
                sourceResourceType,
                sourceResourceId,
                read,
                createdAt,
                readAt
        );
    }

    public void markAsRead() {
        if (this.read) {
            return;
        }

        this.read = true;
        this.readAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getRecipientUserId() {
        return recipientUserId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getSourceService() {
        return sourceService;
    }

    public String getSourceResourceType() {
        return sourceResourceType;
    }

    public UUID getSourceResourceId() {
        return sourceResourceId;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }
}