package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.rest.enums.UserTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIdentityUserRequest(
        @NotNull(message = "Kullanıcı tipi boş olamaz")
        UserTypeRequest userType,
        @Email(message = "Geçerli bir email adresi giriniz")
        @NotBlank(message = "Email boş olamaz")
        String email,
        @NotBlank(message = "Ad boş olamaz")
        String firstName,
        @NotBlank(message = "Soyad boş olamaz")
        String lastName,
        @NotBlank(message = "Telefon boş olamaz")
        String phone,
        @Valid
        @NotNull(message = "Çalışan profili boş olamaz")
        CreateEmployeeProfileRequest employeeProfile,
        @Valid
        @NotNull(message = "Gizli bilgiler boş olamaz")
        CreateEmployeeSensitiveInfoRequest sensitiveInfo
) {
}