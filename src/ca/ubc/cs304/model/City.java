package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record City(Province province, String name, Double taxRate) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO City VALUES ('%s', '%s', %f)"
                .formatted(province.label, name, taxRate);
    }
}
