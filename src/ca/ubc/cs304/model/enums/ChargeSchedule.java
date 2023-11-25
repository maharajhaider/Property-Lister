package ca.ubc.cs304.model.enums;

import java.util.Arrays;

public enum ChargeSchedule {
    FIXED_PRICE("Fixed Price"),
    HOURLY("Hourly");

    public final String label;

    ChargeSchedule(String label) {
        this.label = label;
    }

    public static ChargeSchedule fromLabel(String label) {
        return Arrays.stream(values())
                .filter(e->e.label.equals(label))
                .findFirst()
                .orElseThrow();
    }
}
