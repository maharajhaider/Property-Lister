package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.*;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyForm extends JFrame {
    private JTextField streetAddressTextField;
    private JComboBox<Province> provinceComboBox;
    private JTextField cityNameTextField;
    private JTextField developerLicenseIDTextField;
    private JTextField strataIDTextField;
    private JTextField phoneTextField;
    private JTextField bedroomsTextField;
    private JTextField bathroomsTextField;
    private JTextField sizeInSqftTextField;
    private JCheckBox hasACCheckBox;
    private JTextField listingTypeTextField;
    private JTextField priceTextField;

    private JCheckBox contractorCheckBox;
    private JCheckBox realEstateAgentCheckBox;

    private JComboBox<String> contractorComboBox;
    private JComboBox<String> agentComboBox;

    private boolean isContractorSelected = false;
    private boolean isAgentSelected = false;

    List<RealEstateAgent> realEstateAgents;
    List<ContractorCompany> contractorDataList;

    int contractorID;
    int contractorName;
    int RealEstateID;

    public PropertyForm() {
        // Set up the JFrame
        setTitle("Property Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600); // Increased the height to accommodate more fields
        setLayout(new GridLayout(14, 2, 10, 10)); // Added spacing between rows and columns

        // Create and add components with labels
        addTextFieldWithLabel("Street Address:", streetAddressTextField);
        addComboBoxWithLabel("Province:");
        addTextFieldWithLabel("City Name:", cityNameTextField);
        addTextFieldWithLabel("Developer License ID:", developerLicenseIDTextField);
//        addTextFieldWithLabel("Strata ID:", strataIDTextField);
        addTextFieldWithLabel("Phone:", phoneTextField);
        addTextFieldWithLabel("Bedrooms:", bedroomsTextField);
        addTextFieldWithLabel("Bathrooms:", bathroomsTextField);
        addTextFieldWithLabel("Size in Sqft:", sizeInSqftTextField);
        addCheckBoxWithLabel("Has AC:");
        addTextFieldWithLabel("Listing Type (Sale/Rent):", listingTypeTextField);
        addTextFieldWithLabel("Price:", priceTextField);

        DatabaseConnectionHandler dbhandler = new DatabaseConnectionHandler();


        contractorComboBox = new JComboBox<>(); // Create an empty JComboBox for contractors
//         contractorDataList = dbhandler.getAllEntities(ContractorCompany.class);
//        List<String> contractorNames = new ArrayList<>();
//
//
//        for (ContractorCompany contractorCompany : contractorDataList) {
//            System.out.println(contractorCompany.contractorId());
//            String nameAndId = "ID: " + contractorCompany.contractorId() +", Name: " + contractorCompany.name();
//            contractorNames.add(nameAndId);
//        }
//         contractorComboBox = new JComboBox<>(contractorNames.toArray(new String[0]));




        realEstateAgents = dbhandler.getAllTuples(RealEstateAgent.class);
        List<String> realEstateNames = new ArrayList<>();

        for (RealEstateAgent realEstateAgent : realEstateAgents) {
            String LicenseId = "LicenseID: " + realEstateAgent.agentLicenseId() ;
            realEstateNames.add(LicenseId);
        }

        isAgentSelected = true;
        agentComboBox = new JComboBox<>(realEstateNames.toArray(new String[0]));


        JRadioButton contractorRadioButton = new JRadioButton("Contractor");
        JRadioButton agentRadioButton = new JRadioButton("Real Estate Agent");

        ButtonGroup comboBoxGroup = new ButtonGroup();
        comboBoxGroup.add(contractorRadioButton);
        comboBoxGroup.add(agentRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(contractorRadioButton);
        radioPanel.add(agentRadioButton);

        JPanel comboBoxPanel = new JPanel();
//        comboBoxPanel.add(contractorComboBox);
        comboBoxPanel.add(agentComboBox);

        add(radioPanel);
        add(comboBoxPanel);


        contractorRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isContractorSelected = true;
                isAgentSelected = false;
                agentComboBox.setEnabled(false);
                contractorComboBox.setEnabled(true);

                // Update the content of contractorComboBox
                contractorDataList = dbhandler.getAllTuples(ContractorCompany.class);
                List<String> contractorNames = new ArrayList<>();

                for (ContractorCompany contractorCompany : contractorDataList) {
                    String nameAndId = "ID: " + contractorCompany.contractorId() + ", Name: " + contractorCompany.name();
                    contractorNames.add(nameAndId);
                }
                    contractorComboBox.setModel(new DefaultComboBoxModel<>(contractorNames.toArray(new String[0])));

                    // Replace agentComboBox with contractorComboBox in the comboBoxPanel
                    comboBoxPanel.remove(agentComboBox);
                    comboBoxPanel.add(contractorComboBox);
                    comboBoxPanel.revalidate();
                    comboBoxPanel.repaint();


            }
        });

        agentRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isContractorSelected = false;
                isAgentSelected = true;
                agentComboBox.setEnabled(true);
                contractorComboBox.setEnabled(false);
            }
        });




        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProperty());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void addCheckBoxWithLabel(String labelText, JCheckBox checkBox) {
        add(createLabel(labelText));
        JCheckBox newCheckBox = new JCheckBox();
        add(newCheckBox);
        checkBox = newCheckBox;

        if ("Contractor is managing:".equals(labelText)) {
            contractorCheckBox = checkBox;
        } else if ("Real Estate Agent is managing:".equals(labelText)) {
            realEstateAgentCheckBox = checkBox;
        }
    }
    private void addTextFieldWithLabel(String labelText, JTextField textField) {
        add(createLabel(labelText));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50)); // Set preferred size
        add(textField);

        switch (labelText) {
            case "Street Address:":
                streetAddressTextField = textField;
                break;
            case "City Name:":
                cityNameTextField = textField;
                break;
            case "Developer License ID:":
                developerLicenseIDTextField = textField;
                break;
            case "Strata ID:":
                strataIDTextField = textField;
                break;
            case "Phone:":
                phoneTextField = textField;
                break;
            case "Bedrooms:":
                bedroomsTextField = textField;
                break;
            case "Bathrooms:":
                bathroomsTextField = textField;
                break;
            case "Size in Sqft:":
                sizeInSqftTextField = textField;
                break;
            case "Listing Type (Sale/Rent):":
                listingTypeTextField = textField;
                break;
            case "Price:":
                priceTextField = textField;
                break;
            default:
                break;
        }

    }

    private void addComboBoxWithLabel(String labelText) {
        add(createLabel(labelText));
        JComboBox<Province> comboBox = new JComboBox<>(Province.values());
        add(comboBox);
        provinceComboBox = comboBox;
    }

    private void addCheckBoxWithLabel(String labelText) {
        add(createLabel(labelText));
        JCheckBox checkBox = new JCheckBox();
        add(checkBox);
        hasACCheckBox = checkBox;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    public String validate(String input) {
        if (input == null || (!input.contains(".") && !input.contains("@"))) {
            return input;
        } else {
            return null; // Invalid string
        }
    }

    public int validateInt(int value){

        if(value >= 0){
            return 1;
        }else{
            return -1;
        }

    }

    private RealEstateAgent getRealEstateAgentByLicenseId(int licenseId) {
        for (RealEstateAgent agent : realEstateAgents) {
            if (agent.agentLicenseId() == licenseId) {
                return agent;
            }
        }
        return null; // Return null if the agent with the specified license ID is not found
    }
    private int extractAgentLicenseId(String selectedAgentString) {
        Pattern pattern = Pattern.compile("LicenseID: (\\d+)");
        Matcher matcher = pattern.matcher(selectedAgentString);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1; // This line can be removed if you're confident that the pattern will always match
    }
    private void saveProperty() {
        // Get values from the form
        String streetAddress = validate(streetAddressTextField.getText());
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        String cityName = validate(cityNameTextField.getText());
        int developerLicenseID = validateInt(Integer.parseInt(developerLicenseIDTextField.getText()));
//        int strataID = Integer.parseInt(strataIDTextField.getText());
        String phone = validate(phoneTextField.getText());
        int bedrooms = validateInt(Integer.parseInt(bedroomsTextField.getText()));
        int bathrooms = validateInt(Integer.parseInt(bathroomsTextField.getText()));
        int sizeInSqft = validateInt(Integer.parseInt(sizeInSqftTextField.getText()));
        int hasAC = hasACCheckBox.isSelected() ? 1 : 0;
        String listingType = validate(listingTypeTextField.getText());
        int price = validateInt(Integer.parseInt(priceTextField.getText()));


        if (streetAddress == null || cityName == null || phone == null || listingType == null) {
            // Validation failed, display an error message or handle it as needed
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your input fields.");
            return;
        }

        if(developerLicenseID == -1 || bedrooms == -1 || bathrooms == -1 || sizeInSqft == -1 || price == -1){
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your input fields.");
            return;
        }
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        if(isAgentSelected) {
            String selectedAgentString = (String) agentComboBox.getSelectedItem();
            int agentLicenseId = extractAgentLicenseId(selectedAgentString);

            RealEstateAgent selectedAgent = getRealEstateAgentByLicenseId(agentLicenseId);

            if (selectedAgent != null) {
                String agentPhoneNumber = selectedAgent.phone();
                 new HiresREAForm(agentPhoneNumber,databaseConnectionHandler);
            }
        }
    else {
            String selectedContractor = (String) contractorComboBox.getSelectedItem();

            Pattern pattern = Pattern.compile("ID: (\\d+),");
            Matcher matcher = pattern.matcher(selectedContractor);
            if (matcher.find()) {
                int contractorId = Integer.parseInt(matcher.group(1));
                new HiresContractorForm(streetAddress,selectedProvince,cityName,contractorId,databaseConnectionHandler);
            }
        }

        // Call the appropriate form based on checkbox states
//        if (contractorManaging) {
//            // Create an instance of HiresContractorForm
//            new HiresContractorForm(streetAddress, selectedProvince, cityName);
//
//
//        } else if (realEstateAgentManaging) {
//            // Create an instance of HiresREAForm
//            new HiresREAForm();
//        }

        // Create a Property object
        EntityModel property = new Property(
                streetAddress, selectedProvince, cityName,
                developerLicenseID, null, phone,
                bedrooms, bathrooms, sizeInSqft, hasAC
        );


        HasID listing = new Listing(
                null,
                "replace this",
                streetAddress,
                selectedProvince,
                cityName,
                ListingType.fromLabel(listingType.toLowerCase()), // Set the ListingType to null or any appropriate value
                price, // Set the price to null or any appropriate value
                1// Set the active to null or any appropriate value
        );


        databaseConnectionHandler.insertData(property, null);
        int listingId = databaseConnectionHandler.generateId(listing);
        databaseConnectionHandler.insertData(listing, listingId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PropertyForm());
    }
}