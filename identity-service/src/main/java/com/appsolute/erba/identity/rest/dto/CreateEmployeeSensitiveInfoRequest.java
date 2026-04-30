package com.appsolute.erba.identity.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeSensitiveInfoRequest(
        @NotBlank(message = "National ID boş olamaz")
        String nationalId,
        @NotBlank(message = "SGK numarası boş olamaz")
        String sgkNumber
) {
}