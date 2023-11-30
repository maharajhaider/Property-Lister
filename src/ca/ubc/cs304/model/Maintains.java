package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;
import java.sql.ResultSet;
import java.sql.SQLException;

public record Maintains(
        Integer contractorId,
        String streetAddress,
        Province province,
        String cityName,
        String areaOfResponsibility) implements EntityModel {
    public Maintains(ResultSet rs) throws SQLException {
        this(
                rs.getInt("contractorID"),
                rs.getString("streetAddress").trim(),
                Province.fromLabel(rs.getString("province").trim()),
                rs.getString("cityName").trim(),
                rs.getString("areaOfResponsibility").trim()
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Maintains VALUES (%d, '%s', '%s', '%s', '%s')"
                .formatted(contractorId, streetAddress, province.label, cityName, areaOfResponsibility);
    }
}
