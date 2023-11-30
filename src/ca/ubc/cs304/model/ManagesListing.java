package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record ManagesListing(String realEstateAgentPhone, Integer listingId) implements EntityModel {
    public ManagesListing(ResultSet rs) throws SQLException {
        this(
                rs.getString("realEstateAgentPhone").trim(),
                rs.getInt("listingID")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO ManagesListing VALUES ('%s', %d)"
                .formatted(realEstateAgentPhone, listingId);
    }}
