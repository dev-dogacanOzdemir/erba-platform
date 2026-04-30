package com.appsolute.erba.identity.application.service;

import com.appsolute.erba.identity.application.dto.CreateEmployeeProfileCommand;
import com.appsolute.erba.identity.application.dto.CreateEmployeeSensitiveInfoCommand;
import com.appsolute.erba.identity.application.dto.CreateIdentityUserCommand;
import com.appsolute.erba.identity.application.dto.CreateIdentityUserResult;
import com.appsolute.erba.identity.domain.model.EmployeeProfile;
import com.appsolute.erba.identity.domain.model.EmployeeSensitiveInfo;
import com.appsolute.erba.identity.domain.model.IdentityUser;
import com.appsolute.erba.identity.domain.port.EmployeeProfileRepository;
import com.appsolute.erba.identity.domain.port.EmployeeSensitiveInfoRepository;
import com.appsolute.erba.identity.domain.port.IdentityUserRepository;
import com.appsolute.erba.shared.exception.ConflictException;
import com.appsolute.erba.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateIdentityUserService {

    private final IdentityUserRepository identityUserRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final EmployeeSensitiveInfoRepository employeeSensitiveInfoRepository;

    @Transactional
    public CreateIdentityUserResult create(CreateIdentityUserCommand command) {
        if (command.email() != null && identityUserRepository.existsByEmail(command.email())) {
            throw new ConflictException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        IdentityUser identityUser = IdentityUser.create(
                command.userType(),
                command.email(),
                command.firstName(),
                command.lastName(),
                command.phone()
        );

        IdentityUser savedUser = identityUserRepository.save(identityUser);

        CreateEmployeeProfileCommand profileCommand = command.employeeProfile();

        EmployeeProfile employeeProfile = EmployeeProfile.create(
                savedUser.getId(),
                profileCommand.employeeNumber(),
                profileCommand.department(),
                profileCommand.position(),
                profileCommand.employmentType(),
                profileCommand.hireDate(),
                profileCommand.birthDate()
        );

        employeeProfileRepository.save(employeeProfile);

        CreateEmployeeSensitiveInfoCommand sensitiveCommand = command.sensitiveInfo();

        EmployeeSensitiveInfo sensitiveInfo = EmployeeSensitiveInfo.create(
                savedUser.getId(),
                sensitiveCommand.nationalId(),
                sensitiveCommand.sgkNumber()
        );

        employeeSensitiveInfoRepository.save(sensitiveInfo);

        return new CreateIdentityUserResult(savedUser.getId());
    }
}