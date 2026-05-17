package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.service.FindUsersByPermissionService;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.rest.dto.InternalAuthUserResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal/auth-users")
public class InternalAuthUserController {

    private final FindUsersByPermissionService findUsersByPermissionService;

    public InternalAuthUserController(
            FindUsersByPermissionService findUsersByPermissionService
    ) {
        this.findUsersByPermissionService = findUsersByPermissionService;
    }

    @GetMapping("/by-permission/{permission}")
    public ApiResponse<List<InternalAuthUserResponse>> findByPermission(
            @PathVariable("permission") String permission
    ) {
        List<InternalAuthUserResponse> response =
                findUsersByPermissionService.find(permission)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ApiResponse.success(response);
    }

    private InternalAuthUserResponse toResponse(AuthUser user) {
        return new InternalAuthUserResponse(
                user.getId(),
                user.getEmail()
        );
    }
}