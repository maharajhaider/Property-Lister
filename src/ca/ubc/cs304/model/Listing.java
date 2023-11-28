package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

public record Listing(
        Integer listingId,
        String streetAddress,
        Province province,
        String cityName,
        ListingType type,
        Integer price,
        Integer active) implements HasID {

    public Listing {
        if (active != 0 && active != 1) {
            throw new IllegalArgumentException("active must be 0 or 1");
        }
    }
    
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Listing VALUES (%d, '%s', '%s', '%s', '%s', %d, %d)"
                .formatted(id == null? listingId : id, streetAddress, province.label, cityName, type.label, price, active);
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(listingID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 5000001;
    }
}
