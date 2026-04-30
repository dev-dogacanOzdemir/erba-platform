package com.appsolute.erba.identity.domain.valueobject;

public enum UserType {

    EMPLOYEE("Çalışan"),
    ADMIN("Yönetici");

    private final String label;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}