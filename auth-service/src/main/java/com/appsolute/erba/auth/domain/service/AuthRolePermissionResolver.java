package com.appsolute.erba.auth.domain.service;

import com.appsolute.erba.auth.domain.valueobject.AuthPermission;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.EnumSet;
import java.util.Set;

public class AuthRolePermissionResolver {

    public Set<AuthPermission> resolve(AuthRole role) {
        return switch (role) {
            case ADMIN -> EnumSet.allOf(AuthPermission.class);

            case EMPLOYEE -> EnumSet.of(
                    AuthPermission.PURCHASE_REQUEST_CREATE,
                    AuthPermission.TASK_READ
            );
        };
    }
}