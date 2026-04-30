package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.rest.enums.DepartmentResponse;
import com.appsolute.erba.identity.rest.enums.EmploymentTypeResponse;
import com.appsolute.erba.identity.rest.enums.PositionResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetEmployeeProfileResponse(
        UUID id,
        String employeeNumber,
        DepartmentResponse department,
        PositionResponse position,
        EmploymentTypeResponse employmentType,
        LocalDate hireDate,
        LocalDate terminationDate,
        LocalDate birthDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}