package com.appsolute.erba.purchase.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListPurchaseRequestResponse(
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
}