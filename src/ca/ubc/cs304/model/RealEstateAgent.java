package ca.ubc.cs304.model;

public record RealEstateAgent(String phone, Integer agentLicenseId, Integer yearsOfExp, Integer agencyId)
        implements HasID {
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
