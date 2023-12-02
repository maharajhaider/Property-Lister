package ca.ubc.cs304.model;

import ca.ubc.cs304.model.enums.Province;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ContractorRange {
    private final Integer contractorId;
    private final String contractorName;
    private final Map<Province, Integer> involvementByProvince = new HashMap<>();

    public ContractorRange(Integer contractorId, String contractorName) {
        this.contractorId = contractorId;
        this.contractorName = contractorName;
    }

    public ContractorRange addProvinceData(Province province, Integer citiesInvolved) {
        involvementByProvince.put(province, citiesInvolved);
        return this;
    }

    public Integer contractorId() {
        return contractorId;
    }

    public String contractorName() {
        return contractorName;
    }

    public Map<Province, Integer> involvementByProvince() {
        return involvementByProvince;
    }

    @Override
    public String toString() {
        return "ContractorRange[" +
                "contractorId=" + contractorId + ", " +
                "contractorName=" + contractorName + ", " +
                "involvementByProvince=" + involvementByProvince + ']';
    }
}
