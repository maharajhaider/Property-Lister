package ca.ubc.cs304.model;

public record HiresContractor(String homeownerPhone, Integer contractorId) implements EntityModel {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO HiresContractor VALUES ('%s', %d)"
                .formatted(homeownerPhone, contractorId);
    }}
