package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.util.SimpleResultSet;

public record RealEstateAgency(Integer agencyId, String name, Double rating) implements HasID {
    public RealEstateAgency {
        if (rating != null && (rating < 0.0 || rating > 5.0)) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
    }

    public RealEstateAgency(SimpleResultSet rs) {
        this(
                rs.getInt("agencyID"),
                rs.getString("name"),
                rs.getDouble("rating")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO RealEstateAgency VALUES (%d, '%s', %f)"
                .formatted(id, name, rating);
    }

    @Override
    public String getIdSQL() {
        return "SELECT MAX(agencyID) FROM RealEstateAgency";
    }

    @Override
    public Integer defaultId() {
        return 1000001;
    }
}
