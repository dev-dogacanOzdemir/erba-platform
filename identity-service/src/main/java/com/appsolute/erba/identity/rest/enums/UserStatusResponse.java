package com.appsolute.erba.identity.rest.enums;

public enum UserStatusResponse {
    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    DELETED("Silinmiş");

    private final String label;

    UserStatusResponse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

