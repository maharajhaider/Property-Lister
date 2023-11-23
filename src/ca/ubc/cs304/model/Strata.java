package ca.ubc.cs304.model;

public record Strata(Integer strataId, String name) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO Strata VALUES (%d, '%s')"
                .formatted(strataId, name);
    }}
