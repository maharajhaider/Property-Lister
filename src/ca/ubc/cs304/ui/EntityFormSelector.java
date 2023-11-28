package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityFormSelector extends JFrame {
    private JComboBox<String> entitySelector;
    private Map<String, Supplier<JFrame>> formMap;

    public EntityFormSelector() {
        // Set up the JFrame
        setTitle("Entity Form Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(4, 1, 20, 20)); // Added more spacing between rows and columns

        // Create and add components with labels
        JLabel titleLabel = new JLabel("Select Entity:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        entitySelector = new JComboBox<>(new String[]{
                "City", "ContractorCompany", "Developer", "HiresContractor", "HiresREA",
                "Homeowner", "Maintains", "ManagesListing", "Pays", "Person", "Property",
                "RealEstateAgency", "RealEstateAgent", "Strata"
        });
        entitySelector.setFont(new Font("Arial", Font.PLAIN, 16));
        add(entitySelector);

        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.addActionListener(e -> createAndShowForm());
        add(selectButton);

        // Initialize the form map
        formMap = new HashMap<>();
        formMap.put("City", CityForm::new);
        formMap.put("ContractorCompany", ContractorCompanyForm::new);
        formMap.put("Developer", DeveloperForm::new);
        formMap.put("HiresContractor", HiresContractorForm::new);
        formMap.put("HiresREA", HiresREAForm::new);
        formMap.put("Homeowner", HomeownerForm::new);
        formMap.put("Maintains", MaintainsForm::new);
        formMap.put("ManagesListing", ManagesListingForm::new);
        formMap.put("Pays", PaysForm::new);
        formMap.put("Person", PersonForm::new);
        formMap.put("Property", PropertyForm::new);
        formMap.put("RealEstateAgency", RealEstateAgencyForm::new);
        formMap.put("RealEstateAgent", RealEstateAgentForm::new);
        formMap.put("Strata", StrataForm::new);

        // Display the form
        setVisible(true);
    }

    private void createAndShowForm() {
        String selectedEntity = (String) entitySelector.getSelectedItem();

        if (formMap.containsKey(selectedEntity)) {
            JFrame form = formMap.get(selectedEntity).get();
            form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid entity selection", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EntityFormSelector::new);
    }
}
