package ca.ubc.cs304.model;

import ca.ubc.cs304.model.entity.Listing;
import ca.ubc.cs304.model.entity.Property;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

public record ListingInfo(
        Integer listingId,
        ListingType type,
        Integer price,
        String streetAddress,
        Province province,
        String cityName,
        Integer developerLicenseID,
        Integer strataID,
        String homeownerPhone,
        String realEstateAgentPhone,
        Integer bedrooms,
        Integer bathrooms,
        Integer sizeInSqft,
        Integer hasAC) {
    public ListingInfo(Listing listing, Property property) {
        this(
                listing.listingId(),
                listing.type(),
                listing.price(),
                property.streetAddress(),
                property.province(),
                property.cityName(),
                property.developerLicenseID(),
                property.strataID(),
                property.homeownerPhone(),
                listing.realEstateAgentPhone(),
                property.bedrooms(),
                property.bathrooms(),
                property.sizeInSqft(),
                property.hasAC()
        );
    }
}
