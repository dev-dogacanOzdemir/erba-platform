package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.identity.domain.valueobject.UserType;

import java.util.UUID;

public record UpdateIdentityUserRequest(
        UUID authUserId,
        UserType userType,
        UserStatus status,
        String email,
        String firstName,
        String lastName,
        String phone,
        UUID profilePhotoId,
        UpdateEmployeeProfileRequest employeeProfile
) {
}