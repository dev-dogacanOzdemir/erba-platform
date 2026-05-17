package com.appsolute.erba.purchase.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePurchaseRequestRequest(
        @NotBlank
        @Size(max = 255)
        String productName,

        @NotNull
        @Min(1)
        Integer quantity,

        @Size(max = 2000)
        String description
) {
}