package com.appsolute.erba.identity.rest.enums;

public enum EmploymentTypeRequest {
    FULL_TIME("Tam Zamanlı"),
    PART_TIME("Yarı Zamanlı"),
    INTERN("Stajyer"),
    CONTRACTOR("Sözleşmeli");

    private final String label;

    EmploymentTypeRequest(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

