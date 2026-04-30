package com.appsolute.erba.identity.rest.dto;

public record CreateEmployeeSensitiveInfoRequest(
        String nationalId,
        String sgkNumber
) {
}