package com.appsolute.erba.identity.application.dto;

import com.appsolute.erba.identity.domain.valueobject.UserType;

import java.util.UUID;

public record CreateIdentityUserCommand(
        UUID actorUserId,
        UserType userType,
        String email,
        String firstName,
        String lastName,
        String phone,
        CreateEmployeeProfileCommand employeeProfile,
        CreateEmployeeSensitiveInfoCommand sensitiveInfo
) {
}