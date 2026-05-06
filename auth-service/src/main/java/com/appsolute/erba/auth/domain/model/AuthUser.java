package com.appsolute.erba.auth.domain.model;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthUser {

    private UUID id;
    private String email;
    private String passwordHash;
    private AuthRole role;
    private AuthUserStatus status;
    private int failedLoginCount;
    private LocalDateTime lockedUntil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public AuthUser(
            UUID id,
            String email,
            String passwordHash,
            AuthRole role,
            AuthUserStatus status,
            int failedLoginCount,
            LocalDateTime lockedUntil,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.failedLoginCount = failedLoginCount;
        this.lockedUntil = lockedUntil;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static AuthUser create(
            String email,
            String passwordHash,
            AuthRole role
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new AuthUser(
                UUID.randomUUID(),
                email,
                passwordHash,
                role,
                AuthUserStatus.ACTIVE,
                0,
                null,
                now,
                now,
                null
        );
    }

    public boolean isDeleted() {
        return this.status == AuthUserStatus.DELETED;
    }

    public boolean isActive() {
        return this.status == AuthUserStatus.ACTIVE;
    }

    public boolean isLocked() {
        return this.status == AuthUserStatus.LOCKED
                && lockedUntil != null
                && lockedUntil.isAfter(LocalDateTime.now());
    }

    public void recordFailedLogin(int maxFailedAttempts, long lockMinutes) {
        this.failedLoginCount++;

        if (this.failedLoginCount >= maxFailedAttempts) {
            this.status = AuthUserStatus.LOCKED;
            this.lockedUntil = LocalDateTime.now().plusMinutes(lockMinutes);
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void recordSuccessfulLogin() {
        this.failedLoginCount = 0;
        this.lockedUntil = null;

        if (this.status == AuthUserStatus.LOCKED) {
            this.status = AuthUserStatus.ACTIVE;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = AuthUserStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public AuthRole getRole() {
        return role;
    }

    public AuthUserStatus getStatus() {
        return status;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
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