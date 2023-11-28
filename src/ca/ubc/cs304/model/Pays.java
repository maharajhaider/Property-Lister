package ca.ubc.cs304.model;

public record Pays(String homeownerPhone, Integer strataId, Integer fee) implements EntityModel {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Pays VALUES ('%s', %d, %d)"
                .formatted(homeownerPhone, strataId, fee);
    }}
