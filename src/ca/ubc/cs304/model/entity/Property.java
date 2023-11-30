package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.SimpleResultSet;

public record Property(
        String streetAddress,
        Province province,
        String cityName,
        Integer developerLicenseID,
        Integer strataID,
        String homeownerPhone,
        Integer bedrooms,
        Integer bathrooms,
        Integer sizeInSqft,
        Integer hasAC) implements EntityModel {
    public Property {
        if (hasAC != null && hasAC != 0 && hasAC != 1) {
            throw new IllegalArgumentException("hasAC must be 0 or 1");
        }
    }

    public Property(SimpleResultSet rs) {
        this(
                rs.getString("streetAddress"),
                rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                rs.getString("cityName"),
                rs.getInt("developerLicenseID"),
                rs.getInt("strataID"),
                rs.getString("homeownerPhone"),
                rs.getInt("bedrooms"),
                rs.getInt("bathrooms"),
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
                        homeownerPhone,
                        bedrooms,
                        bathrooms,
                        sizeInSqft,
                        hasAC);
    }
}
