package com.appsolute.erba.identity.domain.valueobject;

public enum Position {

    OWNER("Kurucu"),
    GENERAL_MANAGER("Genel Müdür"),
    MANAGER("Müdür"),
    TEAM_LEAD("Takım Lideri"),
    SPECIALIST("Uzman"),
    ASSISTANT_SPECIALIST("Uzman Yardımcısı"),
    SOFTWARE_ENGINEER("Yazılım Mühendisi"),
    SALES_SPECIALIST("Satış Uzmanı"),
    WAREHOUSE_STAFF("Depo Personeli"),
    CUSTOMER_SUPPORT_STAFF("Müşteri Destek Personeli");

    private final String label;

    Position(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}