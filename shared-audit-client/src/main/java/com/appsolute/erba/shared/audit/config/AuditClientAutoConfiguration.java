package com.appsolute.erba.shared.audit.config;

import com.appsolute.erba.shared.audit.AuditEventPublisher;
import com.appsolute.erba.shared.audit.client.AuditRestClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuditClientProperties.class)
public class AuditClientAutoConfiguration {

    @Bean
    public AuditEventPublisher auditEventPublisher(
            AuditClientProperties properties
    ) {
        return new AuditRestClient(properties);
    }
}