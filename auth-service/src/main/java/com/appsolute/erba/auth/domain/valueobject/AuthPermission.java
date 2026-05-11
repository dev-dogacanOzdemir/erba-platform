package com.appsolute.erba.auth.domain.valueobject;

public enum AuthPermission {

    AUTH_USER_READ("Auth kullanıcılarını görüntüleme"),
    AUTH_USER_MANAGE("Auth kullanıcılarını yönetme"),

    IDENTITY_USER_READ("Kimlik kullanıcılarını görüntüleme"),
    IDENTITY_USER_MANAGE("Kimlik kullanıcılarını yönetme"),

    PURCHASE_REQUEST_CREATE("Satınalma talebi oluşturma"),
    PURCHASE_REQUEST_REVIEW("Satınalma taleplerini inceleme"),
    PURCHASE_REQUEST_APPROVE("Satınalma talebi onaylama"),

    TASK_READ("İş takip kayıtlarını görüntüleme"),
    TASK_MANAGE("İş takip kayıtlarını yönetme");

    private final String label;

    AuthPermission(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}