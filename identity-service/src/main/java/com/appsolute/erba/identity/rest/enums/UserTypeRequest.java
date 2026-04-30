package com.appsolute.erba.identity.rest.enums;

public enum UserTypeRequest {
    EMPLOYEE("Çalışan"),
    ADMIN("Yönetici");

    private final String label;

    UserTypeRequest(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

