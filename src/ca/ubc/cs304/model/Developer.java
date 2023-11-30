package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Developer(Integer developerLicenseId, String name) implements HasID {
    public Developer(ResultSet rs) throws SQLException {
        this(
                rs.getInt("developerLicenseID"),
                rs.getString("name").trim()
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Developer VALUES (%d, '%s')"
                .formatted(developerLicenseId, name);
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(developerLicenseID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 2000001;
    }
}
