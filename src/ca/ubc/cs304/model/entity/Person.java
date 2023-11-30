package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record Person(String phone, String name, String email) implements EntityModel {
    public Person(SimpleResultSet rs) {
        this(
                rs.getString("phone"),
                rs.getString("name"),
                rs.getString("email")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Person VALUES ('%s', '%s', '%s')"
                .formatted(phone, name, email);
    }}
