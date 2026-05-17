package com.appsolute.erba.notification.infrastructure.client;

import com.appsolute.erba.notification.application.port.AuthUserResolver;
import com.appsolute.erba.notification.infrastructure.client.dto.InternalApiResponse;
import com.appsolute.erba.notification.infrastructure.client.dto.InternalAuthUserResponse;
import com.appsolute.erba.notification.infrastructure.config.AuthServiceClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class AuthServiceClient implements AuthUserResolver {

    private final RestClient restClient;
    private final AuthServiceClientProperties properties;

    public AuthServiceClient(
            AuthServiceClientProperties properties
    ) {
        this.properties = properties;

        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    @Override
    public List<ResolvedAuthUser> findUsersByPermission(
            String permission
    ) {

        InternalApiResponse<List<InternalAuthUserResponse>> response =
                restClient.get()
                        .uri("/api/v1/internal/auth-users/by-permission/{permission}",
                                permission)
                        .header(
                                "X-Internal-Service-Token",
                                properties.internalServiceToken()
                        )
                        .retrieve()
                        .body(
                                new ParameterizedTypeReference<>() {
                                }
                        );

        if (response == null || response.data() == null) {
            return List.of();
        }

        return response.data()
                .stream()
                .map(user -> new ResolvedAuthUser(
                        user.userId(),
                        user.email()
                ))
                .toList();
    }
}