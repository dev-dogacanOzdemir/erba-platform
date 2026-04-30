package com.appsolute.erba.auth.domain.valueobject;

import lombok.Getter;

@Getter
public class Email {

    private final String value;

    private Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email boş olamaz");
        }

        if (!value.contains("@")) {
            throw new IllegalArgumentException("Geçersiz email formatı");
        }

        this.value = value.trim().toLowerCase();
    }

    public static Email of(String value) {
        return new Email(value);
    }
}