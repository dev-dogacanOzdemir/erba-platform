package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.rest.enums.DepartmentRequest;
import com.appsolute.erba.identity.rest.enums.EmploymentTypeRequest;
import com.appsolute.erba.identity.rest.enums.PositionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateEmployeeProfileRequest(
        @NotBlank(message = "Personel numarası boş olamaz")
        String employeeNumber,
        @NotNull(message = "Departman boş olamaz")
        DepartmentRequest department,
        @NotNull(message = "Pozisyon boş olamaz")
        PositionRequest position,
        @NotNull(message = "İstihdam tipi boş olamaz")
        EmploymentTypeRequest employmentType,
        @NotNull(message = "İşe giriş tarihi boş olamaz")
        LocalDate hireDate,
        LocalDate terminationDate,
        @NotNull(message = "Doğum tarihi boş olamaz")
        LocalDate birthDate
) {
}