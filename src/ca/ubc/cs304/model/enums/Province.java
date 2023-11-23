package ca.ubc.cs304.model.enums;

public enum Province {
    ONTARIO("ON"),
    QUEBEC("QC"),
    NOVA_SCOTIA("NS"),
    NEW_BRUNSWICK("NB"),
    MANITOBA("MB"),
    BRITISH_COLUMBIA("BC"),
    PRINCE_EDWARD_ISLAND("PE"),
    SASKATCHEWAN("SK"),
    ALBERTA("AB"),
    NEWFOUNDLAND_AND_LABRADOR("NL");

    private String name;

    Province(String name) {
        this.name = name;
    }
}
