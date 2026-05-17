package com.appsolute.erba.notification.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth-service.client")
public record AuthServiceClientProperties(
        String baseUrl,
        String internalServiceToken
) {
}