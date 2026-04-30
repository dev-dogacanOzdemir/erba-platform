package com.appsolute.erba.identity.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetEmployeeProfileResult(
        UUID id,
        String employeeNumber,
        String department,
        String departmentLabel,
        String position,
        String positionLabel,
        String employmentType,
        String employmentTypeLabel,
        LocalDate hireDate,
        LocalDate terminationDate,
        LocalDate birthDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}