package ca.ubc.cs304.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public record HiresREA(String homeownerPhone, String realEstateAgentPhone) implements EntityModel {
    public HiresREA(ResultSet rs) throws SQLException {
        this(
                rs.getString("homeownerPhone").trim(),
                rs.getString("realEstateAgentPhone").trim()
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO HiresREA VALUES ('%s', '%s')"
                .formatted(homeownerPhone, realEstateAgentPhone);
    }}
