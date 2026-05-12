package com.appsolute.erba.auth.infrastructure.client;

import com.appsolute.erba.auth.application.port.AuditEventPublisher;
import com.appsolute.erba.auth.infrastructure.client.dto.CreateAuditLogRequest;
import com.appsolute.erba.auth.infrastructure.config.InternalServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class AuditServiceClient implements AuditEventPublisher {

    private final InternalServiceProperties internalServiceProperties;

    private static final Logger log =
            LoggerFactory.getLogger(AuditServiceClient.class);

    private final RestClient restClient;

    public AuditServiceClient(InternalServiceProperties internalServiceProperties) {
        this.internalServiceProperties = internalServiceProperties;
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }

    @Override
    public void publish(
            UUID actorUserId,
            String action,
            String resourceType,
            UUID resourceId,
            String description
    ) {
        try {
            CreateAuditLogRequest request =
                    new CreateAuditLogRequest(
                            "auth-service",
                            actorUserId,
                            action,
                            resourceType,
                            resourceId,
                            description
                    );

            restClient.post()
                    .uri("/api/v1/audit-logs")
                    .header(
                            "X-Internal-Service-Token",
                            internalServiceProperties.auditServiceToken()
                    )
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception ex) {
            log.error("Failed to send audit log", ex);
        }
    }
}