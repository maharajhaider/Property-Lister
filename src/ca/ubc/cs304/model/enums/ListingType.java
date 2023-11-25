package ca.ubc.cs304.model.enums;

import java.util.Arrays;

public enum ListingType {
    RENT("rent"),
    SALE("sale");

    public final String label;

    ListingType(String label) {
        this.label = label;
    }

    public static ListingType fromLabel(String label) {
        return Arrays.stream(values())
                .filter(e->e.label.equals(label))
                .findFirst()
                .orElseThrow();
    }
}
