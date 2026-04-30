package com.appsolute.erba.identity.application.dto;

import com.appsolute.erba.identity.domain.valueobject.Department;
import com.appsolute.erba.identity.domain.valueobject.EmploymentType;
import com.appsolute.erba.identity.domain.valueobject.Position;

import java.time.LocalDate;

public record UpdateEmployeeProfileCommand(
        String employeeNumber,
        Department department,
        Position position,
        EmploymentType employmentType,
        LocalDate hireDate,
        LocalDate terminationDate,
        LocalDate birthDate
) {
}