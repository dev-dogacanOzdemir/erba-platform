package com.appsolute.erba.notification.infrastructure.client.dto;

public record InternalApiResponse<T>(
        boolean success,
        T data,
        Object error
) {
}