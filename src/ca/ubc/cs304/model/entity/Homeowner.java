package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record Homeowner(String phone) implements EntityModel {
    public Homeowner(SimpleResultSet rs) {
        this(rs.getString("phone"));
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Homeowner VALUES ('%s')"
                .formatted(phone);
    }}
