package ca.ubc.cs304.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Homeowner(String phone) implements EntityModel {
    public Homeowner(ResultSet rs) throws SQLException {
        this(rs.getString("homeownerPhone").trim());
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Homeowner VALUES ('%s')"
                .formatted(phone);
    }}
