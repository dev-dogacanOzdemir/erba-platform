package com.appsolute.erba.auth.domain.valueobject;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserId {

    private final UUID value;

    private UserId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("UserId boş olamaz");
        }
        this.value = value;
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}