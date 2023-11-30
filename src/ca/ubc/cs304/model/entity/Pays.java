package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record Pays(String homeownerPhone, Integer strataId, Integer fee) implements EntityModel {
    public Pays(SimpleResultSet rs) {
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
