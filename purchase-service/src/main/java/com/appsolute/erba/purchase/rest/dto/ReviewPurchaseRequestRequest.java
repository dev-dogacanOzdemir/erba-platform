package com.appsolute.erba.purchase.rest.dto;

import jakarta.validation.constraints.Size;

public record ReviewPurchaseRequestRequest(
        @Size(max = 2000)
        String reviewNote
) {
}