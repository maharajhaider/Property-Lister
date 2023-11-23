package ca.ubc.cs304.model;

public record RealEstateAgency(Integer agencyId, String name, Double rating) implements EntityModel {
    public RealEstateAgency {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
    }
    
    @Override
    public String insertStatement() {
        return "INSERT INTO RealEstateAgency VALUES (%d, '%s', %f)"
                .formatted(agencyId, name, rating);
    }
}
