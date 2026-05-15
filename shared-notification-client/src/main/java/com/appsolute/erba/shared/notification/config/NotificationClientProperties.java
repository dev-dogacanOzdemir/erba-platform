package com.appsolute.erba.shared.notification.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notification.client")
public record NotificationClientProperties(
        String baseUrl,
        String internalServiceToken
) {
}