package com.appsolute.erba.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.appsolute.erba")
@EnableJpaRepositories(basePackages = "com.appsolute.erba.audit.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.appsolute.erba.audit.infrastructure.persistence.entity")
public class AuditServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditServiceApplication.class, args);
    }
}