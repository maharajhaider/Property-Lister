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
        Integer hasAC) {
    public Property {
        if (hasAC != 0 && hasAC != 1) {
            throw new IllegalArgumentException("hasAC must be 0 or 1");
        }
    }
}
