package com.appsolute.erba.identity.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_sensitive_info", schema = "identity")
public class EmployeeSensitiveInfoEntity {

    @Id
    private UUID id;

    @Column(name = "identity_user_id", nullable = false)
    private UUID identityUserId;

    @Column(name = "national_id", length = 20)
    private String nationalId;

    @Column(name = "sgk_number", length = 50)
    private String sgkNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}