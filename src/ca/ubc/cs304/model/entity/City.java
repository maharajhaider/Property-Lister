package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.SimpleResultSet;

public record City(Province province, String name, Double taxRate) implements EntityModel {
    public City(SimpleResultSet rs) {
        this(
                rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                rs.getString("name"),
                rs.getDouble("taxRate")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO City VALUES ('%s', '%s', %f)"
                .formatted(province.label, name, taxRate);
    }
}
