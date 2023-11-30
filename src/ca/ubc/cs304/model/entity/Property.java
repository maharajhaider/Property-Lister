package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.Province;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Property(
        String streetAddress,
        Province province,
        String cityName,
        Integer developerLicenseID,
        Integer strataID,
        String phone,
        Integer bedrooms,
        Integer bathrooms,
        Integer sizeInSqft,
        Integer hasAC) implements EntityModel {
    public Property {
        if (hasAC != 0 && hasAC != 1) {
            throw new IllegalArgumentException("hasAC must be 0 or 1");
        }
    }

    public Property(ResultSet rs) throws SQLException {
        this(
                rs.getString("streetAddress").trim(),
                Province.fromLabel(rs.getString("province").trim()),
                rs.getString("cityName").trim(),
                rs.getInt("developerLicenseID"),
                rs.getInt("strataID"),
                rs.getString("phone").trim(),
                rs.getInt("bedrooms"),
                rs.getInt("bathroom"),
                rs.getInt("sizeInSqft"),
                rs.getInt("hasAC")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Property VALUES ('%s', '%s', '%s', %d, %d, '%s', %d, %d, %d, %d)"
                .formatted(
                        streetAddress,
                        province.label,
                        cityName,
                        developerLicenseID,
                        strataID,
                        phone,
                        bedrooms,
                        bathrooms,
                        sizeInSqft,
                        hasAC);
    }
}
