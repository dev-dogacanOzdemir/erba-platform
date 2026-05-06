package com.appsolute.erba.auth.domain.valueobject;

public enum AuthUserStatus {
    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    LOCKED("Kilitli"),
    DELETED("Silinmiş");

    private final String label;

    AuthUserStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}