package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Pays(String homeownerPhone, Integer strataId, Integer fee) implements EntityModel {
    public Pays(ResultSet rs) throws SQLException {
        this(
                rs.getString("homeownerPhone"),
                rs.getInt("strataID"),
                rs.getInt("fee")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Pays VALUES ('%s', %d, %d)"
                .formatted(homeownerPhone, strataId, fee);
    }}
