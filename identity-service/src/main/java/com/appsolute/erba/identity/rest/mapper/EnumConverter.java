package com.appsolute.erba.identity.rest.mapper;

import com.appsolute.erba.identity.domain.valueobject.*;
import com.appsolute.erba.identity.rest.enums.*;

public class EnumConverter {

    // Request Enum → Domain Enum
    public static UserType toDomain(UserTypeRequest request) {
        if (request == null) return null;
        return UserType.valueOf(request.name());
    }

    public static UserStatus toDomain(UserStatusRequest request) {
        if (request == null) return null;
        return UserStatus.valueOf(request.name());
    }

    public static Department toDomain(DepartmentRequest request) {
        if (request == null) return null;
        return Department.valueOf(request.name());
    }

    public static Position toDomain(PositionRequest request) {
        if (request == null) return null;
        return Position.valueOf(request.name());
    }

    public static EmploymentType toDomain(EmploymentTypeRequest request) {
        if (request == null) return null;
        return EmploymentType.valueOf(request.name());
    }

    // Domain Enum → Response Enum
    public static UserTypeResponse toResponse(UserType domain) {
        if (domain == null) return null;
        return UserTypeResponse.valueOf(domain.name());
    }

    public static UserStatusResponse toResponse(UserStatus domain) {
        if (domain == null) return null;
        return UserStatusResponse.valueOf(domain.name());
    }

    public static DepartmentResponse toResponse(Department domain) {
        if (domain == null) return null;
        return DepartmentResponse.valueOf(domain.name());
    }

    public static PositionResponse toResponse(Position domain) {
        if (domain == null) return null;
        return PositionResponse.valueOf(domain.name());
    }

    public static EmploymentTypeResponse toResponse(EmploymentType domain) {
        if (domain == null) return null;
        return EmploymentTypeResponse.valueOf(domain.name());
    }
}

