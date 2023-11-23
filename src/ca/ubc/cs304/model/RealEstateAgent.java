package ca.ubc.cs304.model;

public record RealEstateAgent(String phone, Integer agentLicenseId, Integer yearsOfExp, Integer agencyId) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO RealEstateAgent VALUES ('%s', %d, %d, %d)"
                .formatted(phone, agentLicenseId, yearsOfExp, agencyId);
    }
}
