package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.service.AuthRolePermissionResolver;
import com.appsolute.erba.auth.domain.valueobject.AuthPermission;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class FindUsersByPermissionService {

    private final AuthUserRepository authUserRepository;
    private final AuthRolePermissionResolver authRolePermissionResolver;

    public FindUsersByPermissionService(
            AuthUserRepository authUserRepository,
            AuthRolePermissionResolver authRolePermissionResolver
    ) {
        this.authUserRepository = authUserRepository;
        this.authRolePermissionResolver = authRolePermissionResolver;
    }

    @Transactional(readOnly = true)
    public List<AuthUser> find(String permission) {
        AuthPermission targetPermission =
                AuthPermission.valueOf(permission.toUpperCase());

        return Arrays.stream(AuthRole.values())
                .filter(role -> authRolePermissionResolver
                        .resolve(role)
                        .contains(targetPermission)
                )
                .flatMap(role -> authUserRepository
                        .findActiveUsersByRole(role)
                        .stream()
                )
                .distinct()
                .toList();
    }
}