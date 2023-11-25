package ca.ubc.cs304.model.enums;

import java.util.Arrays;

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

    public final String label;

    Province(String label) {
        this.label = label;
    }

    public static Province fromLabel(String label) {
        return Arrays.stream(values())
                .filter(e->e.label.equals(label))
                .findFirst()
                .orElseThrow();
    }
}
