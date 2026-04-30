package com.appsolute.erba.identity.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetIdentityUserResult(
        UUID id,
        UUID authUserId,
        String userType,
        String userTypeLabel,
        String status,
        String statusLabel,
        String email,
        String firstName,
        String lastName,
        String phone,
        UUID profilePhotoId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        GetEmployeeProfileResult employeeProfile
) {
}