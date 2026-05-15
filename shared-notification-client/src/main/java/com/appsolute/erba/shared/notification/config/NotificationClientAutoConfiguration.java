package com.appsolute.erba.shared.notification.config;

import com.appsolute.erba.shared.notification.NotificationClient;
import com.appsolute.erba.shared.notification.client.HttpNotificationClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NotificationClientProperties.class)
public class NotificationClientAutoConfiguration {

    @Bean
    public NotificationClient notificationClient(
            NotificationClientProperties properties
    ) {
        return new HttpNotificationClient(properties);
    }
}