package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record RealEstateAgent(String phone, Integer agentLicenseId, Integer yearsOfExp, Integer agencyId)
        implements HasID {
    public RealEstateAgent(SimpleResultSet rs) {
        this(
                rs.getString("phone"),
                rs.getInt("agentLicenseID"),
                rs.getInt("yearsOfExp"),
                rs.getInt("agencyID")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO RealEstateAgent VALUES ('%s', %d, %d, %d)"
                .formatted(phone, agentLicenseId, yearsOfExp, agencyId);
    }

    @Override
    public String getIdSQL() {
        return "SELECT MAX(agentLicenseID) FROM RealEstateAgent";
    }

    @Override
    public Integer defaultId() {
        return 6000001;
    }
}
