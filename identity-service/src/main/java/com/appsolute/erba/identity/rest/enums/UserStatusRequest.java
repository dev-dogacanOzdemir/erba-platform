package com.appsolute.erba.identity.rest.enums;

public enum UserStatusRequest {
    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    DELETED("Silinmiş");

    private final String label;

    UserStatusRequest(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

