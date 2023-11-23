package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record Maintains(
        Integer contractorId,
        String streetAddress,
        Province province,
        String cityName,
        String areaOfResponsibility) {}
