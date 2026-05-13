package com.appsolute.erba.shared.audit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "audit.client")
public record AuditClientProperties(
        String baseUrl,
        String sourceService,
        String internalServiceToken
) {
}