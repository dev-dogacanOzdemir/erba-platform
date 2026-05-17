package com.appsolute.erba.auth.domain.service;

import com.appsolute.erba.auth.domain.valueobject.AuthPermission;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.EnumSet;
import java.util.Set;

import static com.appsolute.erba.auth.domain.valueobject.AuthPermission.NOTIFICATION_READ_OWN;

public class AuthRolePermissionResolver {

    public Set<AuthPermission> resolve(AuthRole role) {
        return switch (role) {
            case ADMIN -> EnumSet.allOf(AuthPermission.class);

            case EMPLOYEE -> EnumSet.of(
                    AuthPermission.PURCHASE_REQUEST_CREATE,
                    AuthPermission.TASK_READ,
                    AuthPermission.PURCHASE_REQUEST_CREATE,
                    AuthPermission.PURCHASE_REQUEST_READ,
                    NOTIFICATION_READ_OWN
            );
        };
    }
}