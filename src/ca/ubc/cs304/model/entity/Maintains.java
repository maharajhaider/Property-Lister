package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.SimpleResultSet;

public record Maintains(
        Integer contractorId,
        String streetAddress,
        Province province,
        String cityName,
        String areaOfResponsibility) implements EntityModel {
    public Maintains(SimpleResultSet rs) {
        this(
                rs.getInt("contractorID"),
                rs.getString("streetAddress"),
                rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                rs.getString("cityName"),
                rs.getString("areaOfResponsibility")
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Maintains VALUES (%d, '%s', '%s', '%s', '%s')"
                .formatted(contractorId, streetAddress, province.label, cityName, areaOfResponsibility);
    }
}
