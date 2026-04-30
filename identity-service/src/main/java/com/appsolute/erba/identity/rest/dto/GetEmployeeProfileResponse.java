package com.appsolute.erba.identity.rest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetEmployeeProfileResponse(
        UUID id,
        String employeeNumber,
        EnumResponse department,
        EnumResponse position,
        EnumResponse employmentType,
        LocalDate hireDate,
        LocalDate terminationDate,
        LocalDate birthDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}