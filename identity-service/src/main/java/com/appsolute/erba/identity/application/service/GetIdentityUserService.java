package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.GetEmployeeProfileResult;
import com.appsolute.erba.identity.application.dto.GetIdentityUserResult;
import com.appsolute.erba.identity.domain.model.EmployeeProfile;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.EmployeeProfileRepository;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetIdentityUserService {

    private final IdentityUserRepository identityUserRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public GetIdentityUserService(
            IdentityUserRepository identityUserRepository,
            EmployeeProfileRepository employeeProfileRepository
    ) {
        this.identityUserRepository = identityUserRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    @Transactional(readOnly = true)
    public GetIdentityUserResult getById(UUID id) {
        IdentityUser identityUser = identityUserRepository.findById(id)
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        EmployeeProfile employeeProfile = employeeProfileRepository
                .findByIdentityUserId(identityUser.getId())
                .orElse(null);

        return toResult(identityUser, employeeProfile);
    }


    private GetIdentityUserResult toResult(
            IdentityUser identityUser,
            EmployeeProfile employeeProfile
    ) {
        return new GetIdentityUserResult(
                identityUser.getId(),
                identityUser.getAuthUserId(),
                identityUser.getUserType().name(),
                identityUser.getUserType().getLabel(),
                identityUser.getStatus().name(),
                identityUser.getStatus().getLabel(),
                identityUser.getEmail(),
                identityUser.getFirstName(),
                identityUser.getLastName(),
                identityUser.getPhone(),
                identityUser.getProfilePhotoId(),
                identityUser.getCreatedAt(),
                identityUser.getUpdatedAt(),
                employeeProfile == null ? null : toProfileResult(employeeProfile)
        );
    }

    private GetEmployeeProfileResult toProfileResult(EmployeeProfile employeeProfile) {
        return new GetEmployeeProfileResult(
                employeeProfile.getId(),
                employeeProfile.getEmployeeNumber(),
                employeeProfile.getDepartment().name(),
                employeeProfile.getDepartment().getLabel(),
                employeeProfile.getPosition().name(),
                employeeProfile.getPosition().getLabel(),
                employeeProfile.getEmploymentType().name(),
                employeeProfile.getEmploymentType().getLabel(),
                employeeProfile.getHireDate(),
                employeeProfile.getTerminationDate(),
                employeeProfile.getBirthDate(),
                employeeProfile.getCreatedAt(),
                employeeProfile.getUpdatedAt()
        );
    }
}