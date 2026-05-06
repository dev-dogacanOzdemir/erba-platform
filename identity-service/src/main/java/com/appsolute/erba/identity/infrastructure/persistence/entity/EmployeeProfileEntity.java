package com.appsolute.erba.identity.infrastructure.persistence.entity;

import com.appsolute.erba.identity.domain.valueobject.Department;
import com.appsolute.erba.identity.domain.valueobject.EmploymentType;
import com.appsolute.erba.identity.domain.valueobject.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_profiles", schema = "identity")
public class EmployeeProfileEntity {

    @Id
    private UUID id;

    @Column(name = "identity_user_id", nullable = false)
    private UUID identityUserId;

    @Column(name = "employee_number", length = 50)
    private String employeeNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 80)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 80)
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false, length = 50)
    private EmploymentType employmentType;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}