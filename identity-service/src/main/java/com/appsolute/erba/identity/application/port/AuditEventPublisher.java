package com.appsolute.erba.identity.application.port;

import java.util.UUID;

public interface AuditEventPublisher {

    void publish(
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            String description
    );
}