package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Listing(
        Integer listingId,
        String realEstateAgentPhone,
        String streetAddress,
        Province province,
        String cityName,
        ListingType type,
        Integer price,
        Integer active) implements HasID {
    public Listing {
        if (active != 0 && active != 1) {
            throw new IllegalArgumentException("active must be 0 or 1");
        }
    }

    public Listing(ResultSet rs) throws SQLException {

        this(
                rs.getInt("listingID"),
                rs.getString("realEstateAgentPhone").trim(),
                rs.getString("streetAddress").trim(),
                Province.fromLabel(rs.getString("province").trim()),
                rs.getString("cityName").trim(),
                ListingType.fromLabel(rs.getString("type").trim()),
                rs.getInt("price"),
                rs.getInt("active")
        );
    }
    
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Listing VALUES (%d, '%s', '%s', '%s', '%s', '%s', %d, %d)"
                .formatted(
                        id == null? listingId : id,
                        realEstateAgentPhone,
                        streetAddress,
                        province.label,
                        cityName,
                        type.label,
                        price,
                        active
                );
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(listingID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 5000001;
    }
}
