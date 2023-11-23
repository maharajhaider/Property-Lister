package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

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

    @Override
    public String insertStatement() {
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
