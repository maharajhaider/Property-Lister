package ca.ubc.cs304.model.enums;

public enum ChargeSchedule {
    FIXED_PRICE("Fixed Price"),
    HOURLY("Hourly");

    public final String label;

    ChargeSchedule(String label) {
        this.label = label;
    }
}
