package com.appsolute.erba.notification.application.port;

import java.util.List;

public interface AuthUserResolver {

    List<ResolvedAuthUser> findUsersByPermission(String permission);

    record ResolvedAuthUser(
            java.util.UUID userId,
            String email
    ) {
    }
}