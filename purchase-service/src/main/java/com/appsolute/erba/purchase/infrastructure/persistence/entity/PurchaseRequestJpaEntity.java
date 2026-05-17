package com.appsolute.erba.purchase.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_requests", schema = "purchase")
public class PurchaseRequestJpaEntity {

    @Id
    private UUID id;

    @Column(name = "requester_user_id", nullable = false)
    private UUID requesterUserId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "reviewed_by_user_id")
    private UUID reviewedByUserId;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "review_note", length = 2000)
    private String reviewNote;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected PurchaseRequestJpaEntity() {
    }

    public PurchaseRequestJpaEntity(
            UUID id,
            UUID requesterUserId,
            String productName,
            Integer quantity,
            String description,
            String status,
            UUID reviewedByUserId,
            LocalDateTime reviewedAt,
            String reviewNote,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.requesterUserId = requesterUserId;
        this.productName = productName;
        this.quantity = quantity;
        this.description = description;
        this.status = status;
        this.reviewedByUserId = reviewedByUserId;
        this.reviewedAt = reviewedAt;
        this.reviewNote = reviewNote;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRequesterUserId() {
        return requesterUserId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public UUID getReviewedByUserId() {
        return reviewedByUserId;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}