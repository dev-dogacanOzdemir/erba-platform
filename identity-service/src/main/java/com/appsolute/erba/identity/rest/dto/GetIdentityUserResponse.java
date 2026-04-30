package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.rest.enums.UserStatusResponse;
import com.appsolute.erba.identity.rest.enums.UserTypeResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetIdentityUserResponse(
        UUID id,
        UUID authUserId,
        UserTypeResponse userType,
        UserStatusResponse status,
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