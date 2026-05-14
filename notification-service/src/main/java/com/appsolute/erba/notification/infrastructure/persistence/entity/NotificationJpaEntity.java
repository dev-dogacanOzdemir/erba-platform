package com.appsolute.erba.notification.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications", schema = "notification")
public class NotificationJpaEntity {

    @Id
    private UUID id;

    @Column(name = "recipient_user_id", nullable = false)
    private UUID recipientUserId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "source_service", length = 100)
    private String sourceService;

    @Column(name = "source_resource_type", length = 100)
    private String sourceResourceType;

    @Column(name = "source_resource_id")
    private UUID sourceResourceId;

    @Column(name = "is_read", nullable = false)
    private boolean read;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    protected NotificationJpaEntity() {
    }

    public NotificationJpaEntity(
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