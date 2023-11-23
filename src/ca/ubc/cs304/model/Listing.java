package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

public record Listing(
        Integer listingID,
        String streetAddress,
        Province province,
        String cityName,
        ListingType type,
        Integer price,
        Integer active) {
    public Listing {
        if (active != 0 && active != 1) {
            throw new IllegalArgumentException("active must be 0 or 1");
        }
    }
}
