package com.appsolute.erba.identity.domain.model;

import com.appsolute.erba.identity.domain.valueobject.Department;
import com.appsolute.erba.identity.domain.valueobject.EmploymentType;
import com.appsolute.erba.identity.domain.valueobject.Position;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class EmployeeProfile {

    private final UUID id;
    private final UUID identityUserId;

    private String employeeNumber;
    private Department department;
    private Position position;
    private EmploymentType employmentType;

    private LocalDate hireDate;
    private LocalDate terminationDate;
    private LocalDate birthDate;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmployeeProfile(
            UUID id,
            UUID identityUserId,
            String employeeNumber,
            Department department,
            Position position,
            EmploymentType employmentType,
            LocalDate hireDate,
            LocalDate terminationDate,
            LocalDate birthDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.identityUserId = identityUserId;
        this.employeeNumber = employeeNumber;
        this.department = department;
        this.position = position;
        this.employmentType = employmentType;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmployeeProfile create(
            UUID identityUserId,
            String employeeNumber,
            Department department,
            Position position,
            EmploymentType employmentType,
            LocalDate hireDate,
            LocalDate birthDate
    ) {
        return new EmployeeProfile(
                UUID.randomUUID(),
                identityUserId,
                employeeNumber,
                department,
                position,
                employmentType,
                hireDate,
                null,
                birthDate,
                LocalDateTime.now(),
                null
        );
    }

    public void update(
            String employeeNumber,
            Department department,
            Position position,
            EmploymentType employmentType,
            LocalDate hireDate,
            LocalDate terminationDate,
            LocalDate birthDate
    ) {
        this.employeeNumber = employeeNumber;
        this.department = department;
        this.position = position;
        this.employmentType = employmentType;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.birthDate = birthDate;
        this.updatedAt = LocalDateTime.now();
    }

    public static EmployeeProfile restore(
            UUID id,
            UUID identityUserId,
            String employeeNumber,
            Department department,
            Position position,
            EmploymentType employmentType,
            LocalDate hireDate,
            LocalDate terminationDate,
            LocalDate birthDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new EmployeeProfile(
                id,
                identityUserId,
                employeeNumber,
                department,
                position,
                employmentType,
                hireDate,
                terminationDate,
                birthDate,
                createdAt,
                updatedAt
        );
    }

    public void updateEmploymentInfo(
            String employeeNumber,
            Department department,
            Position position,
            EmploymentType employmentType,
            LocalDate hireDate,
            LocalDate terminationDate,
            LocalDate birthDate
    ) {
        this.employeeNumber = employeeNumber;
        this.department = department;
        this.position = position;
        this.employmentType = employmentType;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.birthDate = birthDate;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdentityUserId() {
        return identityUserId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public Position getPosition() {
        return position;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}