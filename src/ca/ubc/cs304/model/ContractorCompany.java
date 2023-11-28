package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ChargeSchedule;

public record ContractorCompany(Integer contractorId, String name, ChargeSchedule chargeSchedule)
        implements HasID {

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
