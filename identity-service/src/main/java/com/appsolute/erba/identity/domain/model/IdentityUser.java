package com.appsolute.erba.identity.domain.model;

import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.identity.domain.valueobject.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class IdentityUser {

    private final UUID id;
    private UUID authUserId;

    private UserType userType;
    private UserStatus status;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    private UUID profilePhotoId;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private IdentityUser(
            UUID id,
            UUID authUserId,
            UserType userType,
            UserStatus status,
            String email,
            String firstName,
            String lastName,
            String phone,
            UUID profilePhotoId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.authUserId = authUserId;
        this.userType = userType;
        this.status = status;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.profilePhotoId = profilePhotoId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static IdentityUser create(
            UserType userType,
            String email,
            String firstName,
            String lastName,
            String phone
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new IdentityUser(
                UUID.randomUUID(),
                null,
                userType,
                UserStatus.ACTIVE,
                email,
                firstName,
                lastName,
                phone,
                null,
                now,
                null,
                null
        );
    }

    public static IdentityUser restore(
            UUID id,
            UUID authUserId,
            UserType userType,
            UserStatus status,
            String email,
            String firstName,
            String lastName,
            String phone,
            UUID profilePhotoId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        return new IdentityUser(
                id,
                authUserId,
                userType,
                status,
                email,
                firstName,
                lastName,
                phone,
                profilePhotoId,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public void updateBasicInfo(
            UUID authUserId,
            UserType userType,
            UserStatus status,
            String email,
            String firstName,
            String lastName,
            String phone,
            UUID profilePhotoId
    ) {
        this.authUserId = authUserId;
        this.userType = userType;
        this.status = status;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.profilePhotoId = profilePhotoId;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = UserStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void linkAuthUser(UUID authUserId) {
        if (authUserId == null) {
            throw new IllegalArgumentException("Auth user id boş olamaz.");
        }

        this.authUserId = authUserId;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        if (this.status == UserStatus.DELETED) {
            return;
        }

        this.status = UserStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getAuthUserId() {
        return authUserId;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public UUID getProfilePhotoId() {
        return profilePhotoId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}