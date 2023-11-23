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
        Integer active) implements EntityModel {
    public Listing {
        if (active != 0 && active != 1) {
            throw new IllegalArgumentException("active must be 0 or 1");
        }
    }
    
    @Override
    public String insertStatement() {
        return "INSERT INTO Listing VALUES (%d, '%s', '%s', '%s', '%s', %d, %d)"
                .formatted(listingID, streetAddress, province.label, cityName, type.label, price, active);
    }
}
