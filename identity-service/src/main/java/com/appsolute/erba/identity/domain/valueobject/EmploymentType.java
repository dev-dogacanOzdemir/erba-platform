package com.appsolute.erba.identity.domain.valueobject;

public enum EmploymentType {

    FULL_TIME("Tam Zamanlı"),
    PART_TIME("Yarı Zamanlı"),
    INTERN("Stajyer"),
    CONTRACTOR("Sözleşmeli");

    private final String label;

    EmploymentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}