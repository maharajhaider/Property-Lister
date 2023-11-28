package ca.ubc.cs304.model;

public record Strata(Integer strataId, String name) implements HasID {
    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO Strata VALUES (%d, '%s')"
                .formatted(strataId, name);
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(strataID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 4000001;
    }
}
