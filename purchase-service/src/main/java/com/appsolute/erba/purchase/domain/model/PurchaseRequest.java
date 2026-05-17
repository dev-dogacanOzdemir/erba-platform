package com.appsolute.erba.purchase.domain.model;

import com.appsolute.erba.purchase.domain.valueobject.PurchaseRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseRequest {

    private final UUID id;

    private final UUID requesterUserId;

    private String productName;
    private Integer quantity;
    private String description;

    private PurchaseRequestStatus status;

    private UUID reviewedByUserId;
    private LocalDateTime reviewedAt;
    private String reviewNote;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PurchaseRequest(
            UUID id,
            UUID requesterUserId,
            String productName,
            Integer quantity,
            String description,
            PurchaseRequestStatus status,
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

    public static PurchaseRequest create(
            UUID requesterUserId,
            String productName,
            Integer quantity,
            String description
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new PurchaseRequest(
                UUID.randomUUID(),
                requesterUserId,
                productName,
                quantity,
                description,
                PurchaseRequestStatus.PENDING,
                null,
                null,
                null,
                now,
                now
        );
    }

    public static PurchaseRequest restore(
            UUID id,
            UUID requesterUserId,
            String productName,
            Integer quantity,
            String description,
            PurchaseRequestStatus status,
            UUID reviewedByUserId,
            LocalDateTime reviewedAt,
            String reviewNote,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new PurchaseRequest(
                id,
                requesterUserId,
                productName,
                quantity,
                description,
                status,
                reviewedByUserId,
                reviewedAt,
                reviewNote,
                createdAt,
                updatedAt
        );
    }

    public void approve(
            UUID reviewerUserId,
            String reviewNote
    ) {
        validatePendingState();

        this.status = PurchaseRequestStatus.APPROVED;
        this.reviewedByUserId = reviewerUserId;
        this.reviewedAt = LocalDateTime.now();
        this.reviewNote = reviewNote;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(
            UUID reviewerUserId,
            String reviewNote
    ) {
        validatePendingState();

        this.status = PurchaseRequestStatus.REJECTED;
        this.reviewedByUserId = reviewerUserId;
        this.reviewedAt = LocalDateTime.now();
        this.reviewNote = reviewNote;
        this.updatedAt = LocalDateTime.now();
    }

    private void validatePendingState() {
        if (this.status != PurchaseRequestStatus.PENDING) {
            throw new IllegalStateException(
                    "Purchase request is already reviewed."
            );
        }
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

    public PurchaseRequestStatus getStatus() {
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