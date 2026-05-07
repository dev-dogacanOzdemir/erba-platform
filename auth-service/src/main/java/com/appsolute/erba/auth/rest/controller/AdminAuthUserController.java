package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.*;
import com.appsolute.erba.auth.application.service.ChangeUserRoleService;
import com.appsolute.erba.auth.application.service.ChangeUserStatusService;
import com.appsolute.erba.auth.application.service.CreateAuthUserByAdminService;
import com.appsolute.erba.auth.application.service.ListAuthUsersService;
import com.appsolute.erba.auth.rest.dto.*;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/users")
public class AdminAuthUserController {

    private final ListAuthUsersService listAuthUsersService;
    private final ChangeUserRoleService changeUserRoleService;
    private final ChangeUserStatusService changeUserStatusService;
    private final CreateAuthUserByAdminService createAuthUserByAdminService;

    @GetMapping
    public ApiResponse<List<ListAuthUserResponse>> list() {
        List<ListAuthUserResponse> response = listAuthUsersService.list()
                .stream()
                .map(this::toResponse)
                .toList();

        return ApiResponse.success(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> changeRole(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ChangeUserRoleRequest request
    ) {
        changeUserRoleService.changeRole(
                new ChangeUserRoleCommand(
                        id,
                        request.role()
                )
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ChangeUserStatusRequest request
    ) {
        changeUserStatusService.changeStatus(
                new ChangeUserStatusCommand(
                        id,
                        request.status()
                )
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateAuthUserByAdminResponse> create(
            @Valid @RequestBody CreateAuthUserByAdminRequest request
    ) {
        CreateAuthUserByAdminResult result = createAuthUserByAdminService.create(
                new CreateAuthUserByAdminCommand(
                        request.email(),
                        request.temporaryPassword(),
                        request.role(),
                        request.status()
                )
        );

        return ApiResponse.success(
                new CreateAuthUserByAdminResponse(
                        result.userId(),
                        result.email(),
                        result.role(),
                        result.roleLabel(),
                        result.status(),
                        result.statusLabel()
                )
        );
    }

    private ListAuthUserResponse toResponse(ListAuthUserResult result) {
        return new ListAuthUserResponse(
                result.id(),
                result.email(),
                result.role(),
                result.roleLabel(),
                result.status(),
                result.statusLabel(),
                result.failedLoginCount(),
                result.lockedUntil(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}