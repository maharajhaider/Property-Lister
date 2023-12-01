package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

public record CityRealEstatePrice(
        Province province,
        String cityName,
        ListingType listingType,
        Double avgPricePerSqft) {}
