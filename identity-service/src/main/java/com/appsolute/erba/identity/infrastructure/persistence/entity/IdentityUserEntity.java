package com.appsolute.erba.identity.infrastructure.persistence.entity;

import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.identity.domain.valueobject.UserType;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "identity_users", schema = "identity")
public class IdentityUserEntity {

    @Id
    private UUID id;

    @Column(name = "auth_user_id")
    private UUID authUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserStatus status;

    @Column(length = 150)
    private String email;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 30)
    private String phone;

    @Column(name = "profile_photo_id")
    private UUID profilePhotoId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}