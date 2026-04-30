package com.appsolute.erba.identity.rest.enums;

public enum UserTypeResponse {
    EMPLOYEE("Çalışan"),
    ADMIN("Yönetici");

    private final String label;

    UserTypeResponse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

