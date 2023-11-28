package ca.ubc.cs304.model;

public record ManagesListing(String realEstateAgentPhone, Integer listingId) implements EntityModel {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO ManagesListing VALUES ('%s', %d)"
                .formatted(realEstateAgentPhone, listingId);
    }}
