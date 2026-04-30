package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.domain.valueobject.UserType;

public record CreateIdentityUserRequest(
        UserType userType,
        String email,
        String firstName,
        String lastName,
        String phone,
        CreateEmployeeProfileRequest employeeProfile,
        CreateEmployeeSensitiveInfoRequest sensitiveInfo
) {
}