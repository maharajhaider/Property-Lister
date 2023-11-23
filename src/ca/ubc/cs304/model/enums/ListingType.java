package ca.ubc.cs304.model.enums;

public enum ListingType {
    RENT("rent"),
    SALE("sale");

    public final String name;

    ListingType(String name) {
        this.name = name;
    }
}
