package ca.ubc.cs304.model;

public record Homeowner(String phone) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO Homeowner VALUES ('%s')"
                .formatted(phone);
    }}
