package com.appsolute.erba.identity.rest.controller;

import com.appsolute.erba.identity.application.dto.*;
import com.appsolute.erba.identity.application.service.CreateIdentityUserService;
import com.appsolute.erba.identity.application.service.GetIdentityUserService;
import com.appsolute.erba.identity.application.service.UpdateIdentityUserService;
import com.appsolute.erba.identity.rest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/identity-users")
@RequiredArgsConstructor
public class IdentityUserController {

    private final CreateIdentityUserService createIdentityUserService;
    private final GetIdentityUserService getIdentityUserService;
    private final UpdateIdentityUserService updateIdentityUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateIdentityUserResponse create(@RequestBody CreateIdentityUserRequest request) {
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
            @RequestBody UpdateIdentityUserRequest request
    ) {
        updateIdentityUserService.update(toCommand(id, request));
        return ResponseEntity.noContent().build();
    }

    private CreateIdentityUserCommand toCommand(CreateIdentityUserRequest request) {
        return new CreateIdentityUserCommand(
                request.userType(),
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phone(),
                new CreateEmployeeProfileCommand(
                        request.employeeProfile().employeeNumber(),
                        request.employeeProfile().department(),
                        request.employeeProfile().position(),
                        request.employeeProfile().employmentType(),
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
                new EnumResponse(result.userType(), result.userTypeLabel()),
                new EnumResponse(result.status(), result.statusLabel()),
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
                new EnumResponse(result.department(), result.departmentLabel()),
                new EnumResponse(result.position(), result.positionLabel()),
                new EnumResponse(result.employmentType(), result.employmentTypeLabel()),
                result.hireDate(),
                result.terminationDate(),
                result.birthDate(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    private UpdateIdentityUserCommand toCommand(
            UUID id,
            UpdateIdentityUserRequest request
    ) {
        return new UpdateIdentityUserCommand(
                id,
                request.authUserId(),
                request.userType(),
                request.status(),
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
                request.department(),
                request.position(),
                request.employmentType(),
                request.hireDate(),
                request.terminationDate(),
                request.birthDate()
        );
    }
}