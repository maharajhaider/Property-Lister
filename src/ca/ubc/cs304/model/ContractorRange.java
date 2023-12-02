package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

public record ContractorRange(Integer contractorId, String contractorName, Province province, Integer citiesInvolved) {
}
