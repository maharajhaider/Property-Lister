package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Person(String phone, String name, String email) implements EntityModel {
    public Person(ResultSet rs) throws SQLException {
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
