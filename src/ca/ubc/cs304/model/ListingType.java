package ca.ubc.cs304.model;

public enum ListingType {
    RENT("rent"),
    SALE("sale");

    public final String label;

    ListingType(String label) {
        this.label = label;
    }
}
