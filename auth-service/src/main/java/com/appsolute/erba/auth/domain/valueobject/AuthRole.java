package com.appsolute.erba.auth.domain.valueobject;

public enum AuthRole {
    EMPLOYEE("Çalışan"),
    ADMIN("Yönetici");

    private final String label;

    AuthRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}