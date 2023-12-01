package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record Developer(Integer developerLicenseId, String name) implements HasID {
    public Developer(SimpleResultSet rs) {
        this(
                rs.getInt("developerLicenseID"),
                rs.getString("name")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Developer VALUES (%d, '%s')"
                .formatted(developerLicenseId, name);
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(developerLicenseID) FROM Developer";
    }

    @Override
    public Integer defaultId() {
        return 2000001;
    }
}
