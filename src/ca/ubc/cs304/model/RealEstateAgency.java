package ca.ubc.cs304.model;

public record RealEstateAgency(Integer agencyId, String name, Double rating) {
    public RealEstateAgency {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
    }
}
