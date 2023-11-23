package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ChargeSchedule;

public record ContractorCompany(Integer contractorId, String name, ChargeSchedule chargeSchedule) implements EntityModel {
    @Override
    public String insertStatement() {
        return "INSERT INTO ContractorCompany VALUES (%d, '%s', '%s')"
                .formatted(contractorId, name, chargeSchedule.label);
    }
}
