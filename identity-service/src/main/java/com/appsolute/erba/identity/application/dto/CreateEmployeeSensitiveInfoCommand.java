package com.appsolute.erba.identity.application.dto;

public record CreateEmployeeSensitiveInfoCommand(
        String nationalId,
        String sgkNumber
) {
}