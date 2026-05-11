package com.appsolute.erba.identity.application.dto;

import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.identity.domain.valueobject.UserType;

import java.util.UUID;

public record UpdateIdentityUserCommand(
        UUID actorUserId,
        UUID id,
        UUID authUserId,
        UserType userType,
        UserStatus status,
        String email,
        String firstName,
        String lastName,
        String phone,
        UUID profilePhotoId,
        UpdateEmployeeProfileCommand employeeProfile
) {
}