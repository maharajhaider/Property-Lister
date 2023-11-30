package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record HiresContractor(String homeownerPhone, Integer contractorId) implements EntityModel {
    public HiresContractor(ResultSet rs) throws SQLException {
        this(
                rs.getString("homeownerPhone").trim(),
                rs.getInt("contractorID")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO HiresContractor VALUES ('%s', %d)"
                .formatted(homeownerPhone, contractorId);
    }}
