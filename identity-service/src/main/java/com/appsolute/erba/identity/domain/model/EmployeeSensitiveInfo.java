package com.appsolute.erba.identity.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployeeSensitiveInfo {

    private final UUID id;
    private final UUID identityUserId;

    private String nationalId;
    private String sgkNumber;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmployeeSensitiveInfo(
            UUID id,
            UUID identityUserId,
            String nationalId,
            String sgkNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.identityUserId = identityUserId;
        this.nationalId = nationalId;
        this.sgkNumber = sgkNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmployeeSensitiveInfo create(
            UUID identityUserId,
            String nationalId,
            String sgkNumber
    ) {
        return new EmployeeSensitiveInfo(
                UUID.randomUUID(),
                identityUserId,
                nationalId,
                sgkNumber,
                LocalDateTime.now(),
                null
        );
    }

    public static EmployeeSensitiveInfo restore(
            UUID id,
            UUID identityUserId,
            String nationalId,
            String sgkNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new EmployeeSensitiveInfo(
                id,
                identityUserId,
                nationalId,
                sgkNumber,
                createdAt,
                updatedAt
        );
    }

    public void updateSensitiveInfo(
            String nationalId,
            String sgkNumber
    ) {
        this.nationalId = nationalId;
        this.sgkNumber = sgkNumber;
        this.updatedAt = LocalDateTime.now();
    }

    public String getMaskedNationalId() {
        if (nationalId == null || nationalId.length() < 4) {
            return null;
        }

        return nationalId.substring(0, 3)
                + "*****"
                + nationalId.substring(nationalId.length() - 2);
    }

    public String getMaskedSgkNumber() {
        if (sgkNumber == null || sgkNumber.length() < 4) {
            return null;
        }

        return sgkNumber.substring(0, 3)
                + "*****";
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdentityUserId() {
        return identityUserId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getSgkNumber() {
        return sgkNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}