package com.appsolute.erba.identity.rest.dto;

import com.appsolute.erba.identity.rest.enums.UserStatusRequest;
import com.appsolute.erba.identity.rest.enums.UserTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateIdentityUserRequest(
        @NotNull(message = "Auth user id boş olamaz")
        UUID authUserId,
        @NotNull(message = "Kullanıcı tipi boş olamaz")
        UserTypeRequest userType,
        @NotNull(message = "Durum boş olamaz")
        UserStatusRequest status,
        @Email(message = "Geçerli bir email adresi giriniz")
        @NotBlank(message = "Email boş olamaz")
        String email,
        @NotBlank(message = "Ad boş olamaz")
        String firstName,
        @NotBlank(message = "Soyad boş olamaz")
        String lastName,
        @NotBlank(message = "Telefon boş olamaz")
        String phone,
        UUID profilePhotoId,
        @Valid
        @NotNull(message = "Çalışan profili boş olamaz")
        UpdateEmployeeProfileRequest employeeProfile
) {
}