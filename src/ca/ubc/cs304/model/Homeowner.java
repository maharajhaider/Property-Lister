package ca.ubc.cs304.model;

public record Homeowner(String phone) implements EntityModel {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Homeowner VALUES ('%s')"
                .formatted(phone);
    }}
