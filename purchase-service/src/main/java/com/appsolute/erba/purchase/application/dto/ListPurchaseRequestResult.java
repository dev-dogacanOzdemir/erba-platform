package com.appsolute.erba.purchase.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListPurchaseRequestResult(
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