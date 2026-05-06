package com.appsolute.erba.identity.rest.controller;

import com.appsolute.erba.identity.application.dto.CreateEmployeeProfileCommand;
import com.appsolute.erba.identity.application.dto.CreateEmployeeSensitiveInfoCommand;
import com.appsolute.erba.identity.application.dto.CreateIdentityUserCommand;
import com.appsolute.erba.identity.application.dto.CreateIdentityUserResult;
import com.appsolute.erba.identity.application.dto.GetEmployeeProfileResult;
import com.appsolute.erba.identity.application.dto.GetIdentityUserResult;
import com.appsolute.erba.identity.application.dto.UpdateEmployeeProfileCommand;
import com.appsolute.erba.identity.application.dto.UpdateIdentityUserCommand;
import com.appsolute.erba.identity.application.service.CreateIdentityUserService;
import com.appsolute.erba.identity.application.service.DeleteIdentityUserService;
import com.appsolute.erba.identity.application.service.GetIdentityUserService;
import com.appsolute.erba.identity.application.service.UpdateIdentityUserService;
import com.appsolute.erba.identity.domain.valueobject.Department;
import com.appsolute.erba.identity.domain.valueobject.EmploymentType;
import com.appsolute.erba.identity.domain.valueobject.Position;
import com.appsolute.erba.identity.domain.valueobject.UserStatus;
import com.appsolute.erba.identity.domain.valueobject.UserType;
import com.appsolute.erba.identity.rest.dto.CreateIdentityUserRequest;
import com.appsolute.erba.identity.rest.dto.CreateIdentityUserResponse;
import com.appsolute.erba.identity.rest.dto.GetEmployeeProfileResponse;
import com.appsolute.erba.identity.rest.dto.GetIdentityUserResponse;
import com.appsolute.erba.identity.rest.dto.UpdateEmployeeProfileRequest;
import com.appsolute.erba.identity.rest.dto.UpdateIdentityUserRequest;
import com.appsolute.erba.identity.rest.mapper.EnumConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/identity-users")
@RequiredArgsConstructor
public class IdentityUserController {

    private final CreateIdentityUserService createIdentityUserService;
    private final GetIdentityUserService getIdentityUserService;
    private final UpdateIdentityUserService updateIdentityUserService;
    private final DeleteIdentityUserService deleteIdentityUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateIdentityUserResponse create(@Valid @RequestBody CreateIdentityUserRequest request) {
        CreateIdentityUserResult result = createIdentityUserService.create(toCommand(request));
        return new CreateIdentityUserResponse(result.identityUserId());
    }

    @GetMapping("/{id}")
    public GetIdentityUserResponse getById(@PathVariable("id") UUID id) {
        GetIdentityUserResult result = getIdentityUserService.getById(id);
        return toResponse(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateIdentityUserRequest request
    ) {
        updateIdentityUserService.update(toCommand(id, request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        deleteIdentityUserService.delete(id);
    }

    private CreateIdentityUserCommand toCommand(CreateIdentityUserRequest request) {
        return new CreateIdentityUserCommand(
                EnumConverter.toDomain(request.userType()),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phone(),
                new CreateEmployeeProfileCommand(
                        request.employeeProfile().employeeNumber(),
                        EnumConverter.toDomain(request.employeeProfile().department()),
                        EnumConverter.toDomain(request.employeeProfile().position()),
                        EnumConverter.toDomain(request.employeeProfile().employmentType()),
                        request.employeeProfile().hireDate(),
                        request.employeeProfile().birthDate()
                ),
                new CreateEmployeeSensitiveInfoCommand(
                        request.sensitiveInfo().nationalId(),
                        request.sensitiveInfo().sgkNumber()
                )
        );
    }

    private GetIdentityUserResponse toResponse(GetIdentityUserResult result) {
        return new GetIdentityUserResponse(
                result.id(),
                result.authUserId(),
                EnumConverter.toResponse(UserType.valueOf(result.userType())),
                EnumConverter.toResponse(UserStatus.valueOf(result.status())),
                result.email(),
                result.firstName(),
                result.lastName(),
                result.phone(),
                result.profilePhotoId(),
                result.createdAt(),
                result.updatedAt(),
                result.employeeProfile() == null ? null : toEmployeeProfileResponse(result.employeeProfile())
        );
    }

    private GetEmployeeProfileResponse toEmployeeProfileResponse(GetEmployeeProfileResult result) {
        return new GetEmployeeProfileResponse(
                result.id(),
                result.employeeNumber(),
                EnumConverter.toResponse(Department.valueOf(result.department())),
                EnumConverter.toResponse(Position.valueOf(result.position())),
                EnumConverter.toResponse(EmploymentType.valueOf(result.employmentType())),
                result.hireDate(),
                result.terminationDate(),
                result.birthDate(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    private UpdateIdentityUserCommand toCommand(UUID id, UpdateIdentityUserRequest request) {
        return new UpdateIdentityUserCommand(
                id,
                request.authUserId(),
                EnumConverter.toDomain(request.userType()),
                EnumConverter.toDomain(request.status()),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.profilePhotoId(),
                request.employeeProfile() == null ? null : toCommand(request.employeeProfile())
        );
    }

    private UpdateEmployeeProfileCommand toCommand(UpdateEmployeeProfileRequest request) {
        return new UpdateEmployeeProfileCommand(
                request.employeeNumber(),
                EnumConverter.toDomain(request.department()),
                EnumConverter.toDomain(request.position()),
                EnumConverter.toDomain(request.employmentType()),
                request.hireDate(),
                request.terminationDate(),
                request.birthDate()
        );
    }
}