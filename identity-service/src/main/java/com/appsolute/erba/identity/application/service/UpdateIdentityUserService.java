package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.UpdateEmployeeProfileCommand;
import com.appsolute.erba.identity.application.dto.UpdateIdentityUserCommand;
import com.appsolute.erba.identity.domain.model.EmployeeProfile;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.EmployeeProfileRepository;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateIdentityUserService {

    private final IdentityUserRepository identityUserRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final AuditEventPublisher auditEventPublisher;

    public UpdateIdentityUserService(
            IdentityUserRepository identityUserRepository,
            EmployeeProfileRepository employeeProfileRepository,
            AuditEventPublisher auditEventPublisher
    ) {
        this.identityUserRepository = identityUserRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public void update(UpdateIdentityUserCommand command) {
        IdentityUser identityUser = identityUserRepository.findById(command.id())
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        identityUser.updateBasicInfo(
                command.authUserId(),
                command.userType(),
                command.status(),
                command.email(),
                command.firstName(),
                command.lastName(),
                command.phone(),
                command.profilePhotoId()
        );

        identityUserRepository.save(identityUser);

        if (command.employeeProfile() != null) {
            updateEmployeeProfile(identityUser, command.employeeProfile());
        }

        auditEventPublisher.publish(
                command.actorUserId(),
                "IDENTITY_USER_UPDATED",
                "IDENTITY_USER",
                identityUser.getId(),
                "Identity user updated: " + identityUser.getEmail()
        );
    }

    private void updateEmployeeProfile(
            IdentityUser identityUser,
            UpdateEmployeeProfileCommand command
    ) {
        EmployeeProfile employeeProfile = employeeProfileRepository
                .findByIdentityUserId(identityUser.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        employeeProfile.update(
                command.employeeNumber(),
                command.department(),
                command.position(),
                command.employmentType(),
                command.hireDate(),
                command.terminationDate(),
                command.birthDate()
        );

        employeeProfileRepository.save(employeeProfile);
    }
}