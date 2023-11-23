package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.ChargeSchedule;

public record ContractorCompany(Integer contractorId, String name, ChargeSchedule chargeSchedule) {}
