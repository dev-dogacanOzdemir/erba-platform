package com.appsolute.erba.purchase.application.dto;

import java.util.UUID;

public record CreatePurchaseRequestCommand(
        UUID requesterUserId,
        String productName,
        Integer quantity,
        String description
) {
}