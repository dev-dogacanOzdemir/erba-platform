package com.appsolute.erba.shared.exception;

public enum ErrorCode {

    // AUTH
    INVALID_CREDENTIALS("AUTH_001", "Geçersiz e-posta veya parola"),
    TOKEN_EXPIRED("AUTH_002", "Token süresi dolmuş"),
    UNAUTHORIZED("AUTH_003", "Yetkisiz erişim"),

    // USER
    USER_NOT_FOUND("USER_001", "Kullanıcı bulunamadı"),
    USER_DISABLED("USER_002", "Kullanıcı pasif"),
    EMAIL_ALREADY_EXISTS("USER_003", "Bu e-posta zaten kullanımda"),

    // PERMISSION
    FORBIDDEN("PERMISSION_001", "Bu işlem için yetkiniz yok"),

    // VALIDATION
    VALIDATION_ERROR("COMMON_001", "Geçersiz istek"),

    // SYSTEM
    INTERNAL_ERROR("SYSTEM_001", "Beklenmeyen hata"),

    //IDENTITY
    AUTH_USER_ALREADY_LINKED("IDENTITY_001", "Auth kullanıcısı zaten başka bir kimlik kaydına bağlı"),

    //NOTIFICATION
    NOTIFICATION_NOT_FOUND("NOTIFICATION_001", "Bildirim bulunamadı"),

    //PURCHASE
    PURCHASE_REQUEST_NOT_FOUND("PURCHASE_001", "Satınalma talebi bulunamadı");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}