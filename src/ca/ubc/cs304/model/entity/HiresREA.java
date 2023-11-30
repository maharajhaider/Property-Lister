package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record HiresREA(String homeownerPhone, String realEstateAgentPhone) implements EntityModel {
    public HiresREA(SimpleResultSet rs) {
        this(
                rs.getString("homeownerPhone"),
                rs.getString("realEstateAgentPhone")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO HiresREA VALUES ('%s', '%s')"
                .formatted(homeownerPhone, realEstateAgentPhone);
    }}
