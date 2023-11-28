package ca.ubc.cs304.model;

public record Person(String phone, String name, String email) implements EntityModel {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Person VALUES ('%s', '%s', '%s')"
                .formatted(phone, name, email);
    }}
