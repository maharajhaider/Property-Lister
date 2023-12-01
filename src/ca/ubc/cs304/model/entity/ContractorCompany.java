package ca.ubc.cs304.model.entity;

import ca.ubc.cs304.model.enums.ChargeSchedule;
import ca.ubc.cs304.util.SimpleResultSet;

public record ContractorCompany(Integer contractorId, String name, ChargeSchedule chargeSchedule)
        implements HasID {
    public ContractorCompany(SimpleResultSet rs) {
        this(
                rs.getInt("contractorID"),
                rs.getString("name"),
                rs.getString("chargeSchedule") == null? null: ChargeSchedule.fromLabel(rs.getString("chargeSchedule"))
        );
    }

    @Override
    public String insertStatement(Integer id) {
        return "INSERT INTO ContractorCompany VALUES (%d, '%s', '%s')"
                .formatted(id == null? contractorId: id, name, chargeSchedule.label);
    }

    @Override
    public String getIdSQL() {
        return "SELECT MAX(contractorID) FROM ContractorCompany";
    }

    @Override
    public Integer defaultId() {
        return 3000001;
    }
}
