package com.appsolute.erba.identity.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetIdentityUserResponse(
        UUID id,
        UUID authUserId,
        EnumResponse userType,
        EnumResponse status,
        String email,
        String firstName,
        String lastName,
        String phone,
        UUID profilePhotoId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        GetEmployeeProfileResponse employeeProfile
) {
}