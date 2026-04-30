package com.appsolute.erba.identity.domain.valueobject;

public enum UserStatus {

    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    DELETED("Silinmiş");

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}