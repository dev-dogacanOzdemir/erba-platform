package com.appsolute.erba.shared.audit;

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