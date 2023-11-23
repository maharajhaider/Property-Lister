package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record Maintains(
        Integer contractorId,
        String streetAddress,
        Province province,
        String cityName,
        String areaOfResponsibility) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO Maintains VALUES (%d, '%s', '%s', '%s', '%s')"
                .formatted(contractorId, streetAddress, province.label, cityName, areaOfResponsibility);
    }
}
