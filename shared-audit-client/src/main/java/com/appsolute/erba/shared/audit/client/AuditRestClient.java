package com.appsolute.erba.shared.audit.client;

import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.audit.config.AuditClientProperties;
import com.appsolute.erba.shared.audit.dto.CreateAuditLogRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.UUID;

public class AuditRestClient implements AuditEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(AuditRestClient.class);

    private static final String INTERNAL_SERVICE_TOKEN_HEADER =
            "X-Internal-Service-Token";

    private final RestClient restClient;
    private final AuditClientProperties properties;

    public AuditRestClient(AuditClientProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
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
            CreateAuditLogRequest request = new CreateAuditLogRequest(
                    properties.sourceService(),
                    actorUserId,
                    action,
                    resourceType,
                    resourceId,
                    description
            );

            restClient.post()
                    .uri("/api/v1/audit-logs")
                    .header(
                            INTERNAL_SERVICE_TOKEN_HEADER,
                            properties.internalServiceToken()
                    )
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception ex) {
            log.error(
                    "Failed to publish audit event. action={}, resourceType={}, resourceId={}",
                    action,
                    resourceType,
                    resourceId,
                    ex
            );
        }
    }
}