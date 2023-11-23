package ca.ubc.cs304.model;

public record Developer(Integer developerLicenseId, String name) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO Developer VALUES (%d, '%s')"
                .formatted(developerLicenseId, name);
    }
}
