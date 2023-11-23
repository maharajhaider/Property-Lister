package ca.ubc.cs304.model.enums;

public enum ChargeSchedule {
    FIXED_PRICE("Fixed Price"),
    HOURLY("Hourly");

    private String name;

    ChargeSchedule(String name) {
        this.name = name;
    }
}
