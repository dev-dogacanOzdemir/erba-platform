package com.appsolute.erba.auth.domain.valueobject;

import lombok.Getter;

@Getter
public class PasswordHash {

    private final String value;

    private PasswordHash(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password hash boş olamaz");
        }
        this.value = value;
    }

    public static PasswordHash of(String value) {
        return new PasswordHash(value);
    }
}