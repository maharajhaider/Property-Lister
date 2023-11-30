package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.ChargeSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public record ContractorCompany(Integer contractorId, String name, ChargeSchedule chargeSchedule)
        implements HasID {
    public ContractorCompany(ResultSet rs) throws SQLException {
        this(
                rs.getInt("contractorID"),
                rs.getString("name").trim(),
                ChargeSchedule.fromLabel(rs.getString("chargeSchedule").trim())
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO ContractorCompany VALUES (%d, '%s', '%s')"
                .formatted(id == null? contractorId: id, name, chargeSchedule.label);
    }

    @Override
    public String getIdSQL() {
        return "SELECT max(contractorID) FROM Listing";
    }

    @Override
    public Integer defaultId() {
        return 3000001;
    }
}
