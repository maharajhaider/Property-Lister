package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record HiresContractor(String homeownerPhone, Integer contractorId) implements EntityModel {
    public HiresContractor(SimpleResultSet rs) {
        this(
                rs.getString("homeownerPhone"),
                rs.getInt("contractorID")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO HiresContractor VALUES ('%s', %d)"
                .formatted(homeownerPhone, contractorId);
    }}
