package com.appsolute.erba.shared.notification.client;

import com.appsolute.erba.shared.notification.NotificationClient;
import com.appsolute.erba.shared.notification.config.NotificationClientProperties;
import com.appsolute.erba.shared.notification.dto.CreateNotificationRequest;
import com.appsolute.erba.shared.notification.dto.SendTargetedNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.UUID;

public class HttpNotificationClient implements NotificationClient {

    private static final Logger log =
            LoggerFactory.getLogger(HttpNotificationClient.class);

    private static final String INTERNAL_SERVICE_TOKEN_HEADER =
            "X-Internal-Service-Token";

    private final RestClient restClient;
    private final NotificationClientProperties properties;

    public HttpNotificationClient(NotificationClientProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    @Override
    public void send(
            UUID recipientUserId,
            String title,
            String message,
            String type,
            String sourceService,
            String sourceResourceType,
            UUID sourceResourceId
    ) {
        try {
            CreateNotificationRequest request = new CreateNotificationRequest(
                    recipientUserId,
                    title,
                    message,
                    type,
                    sourceService,
                    sourceResourceType,
                    sourceResourceId
            );

            restClient.post()
                    .uri("/api/v1/notifications")
                    .header(
                            INTERNAL_SERVICE_TOKEN_HEADER,
                            properties.internalServiceToken()
                    )
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception ex) {
            log.error(
                    "Failed to send notification. recipientUserId={}, type={}, sourceService={}, sourceResourceType={}, sourceResourceId={}",
                    recipientUserId,
                    type,
                    sourceService,
                    sourceResourceType,
                    sourceResourceId,
                    ex
            );
        }
    }
    @Override
    public void sendTargetedNotification(
            SendTargetedNotificationRequest request
    ) {
        restClient.post()
                .uri("/api/v1/internal/notifications/targeted")
                .header(
                        "X-Internal-Service-Token",
                        properties.internalServiceToken()
                )
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}