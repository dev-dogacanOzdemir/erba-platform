package com.appsolute.erba.identity.domain.valueobject;

public enum Department {

    MANAGEMENT("Yönetim"),
    HUMAN_RESOURCES("İnsan Kaynakları"),
    FINANCE("Finans"),
    SALES("Satış"),
    MARKETING("Pazarlama"),
    OPERATIONS("Operasyon"),
    IT("Bilgi Teknolojileri"),
    WAREHOUSE("Depo"),
    CUSTOMER_SUPPORT("Müşteri Destek");

    private final String label;

    Department(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}