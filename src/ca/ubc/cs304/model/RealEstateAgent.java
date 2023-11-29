package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record RealEstateAgent(String phone, Integer agentLicenseId, Integer yearsOfExp, Integer agencyId)
        implements HasID {
    public RealEstateAgent(ResultSet rs) throws SQLException {
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
        return "SELECT max(agentLicenseID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 6000001;
    }
}
