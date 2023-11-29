package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

import java.sql.ResultSet;
import java.sql.SQLException;

public record City(Province province, String name, Double taxRate) implements EntityModel {
    public City(ResultSet rs) throws SQLException {
        this(
                Province.fromLabel(rs.getString("province")),
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
