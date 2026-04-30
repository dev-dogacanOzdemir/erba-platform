package com.appsolute.erba.identity.rest.enums;

public enum EmploymentTypeResponse {
    FULL_TIME("Tam Zamanlı"),
    PART_TIME("Yarı Zamanlı"),
    INTERN("Stajyer"),
    CONTRACTOR("Sözleşmeli");

    private final String label;

    EmploymentTypeResponse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

