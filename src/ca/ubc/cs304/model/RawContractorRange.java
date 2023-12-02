package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

import java.util.Map;

public record RawContractorRange(Integer contractorId, String contractorName, Province province, Integer citiesInvolved) {
}
