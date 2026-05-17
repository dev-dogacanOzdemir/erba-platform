package com.appsolute.erba.purchase.application.dto;

import java.util.UUID;

public record ReviewPurchaseRequestCommand(
        UUID reviewerUserId,
        UUID purchaseRequestId,
        String reviewNote
) {
}