package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.SimpleResultSet;

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
        if (active != null && active != 0 && active != 1) {
            throw new IllegalArgumentException("active must be 0 or 1");
        }
    }

    public Listing(SimpleResultSet rs) {
        this(
                rs.getInt("listingID"),
                rs.getString("realEstateAgentPhone"),
                rs.getString("streetAddress"),
                rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                rs.getString("cityName"),
                rs.getString("type") == null? null: ListingType.fromLabel(rs.getString("type")),
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
        return "SELECT MAX(listingID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 5000001;
    }
}
