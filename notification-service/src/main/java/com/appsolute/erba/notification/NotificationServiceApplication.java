package com.appsolute.erba.notification;

import com.appsolute.erba.notification.infrastructure.config.AuthServiceClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.appsolute.erba")
@EnableJpaRepositories(basePackages = "com.appsolute.erba.notification.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.appsolute.erba.notification.infrastructure.persistence.entity")
@EnableConfigurationProperties(AuthServiceClientProperties.class)
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}