package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Listing;
import ca.ubc.cs304.model.Property;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;

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
        addTextFieldWithLabel("Strata ID:", strataIDTextField);
        addTextFieldWithLabel("Phone:", phoneTextField);
        addTextFieldWithLabel("Bedrooms:", bedroomsTextField);
        addTextFieldWithLabel("Bathrooms:", bathroomsTextField);
        addTextFieldWithLabel("Size in Sqft:", sizeInSqftTextField);
        addCheckBoxWithLabel("Has AC:");
        addTextFieldWithLabel("Listing Type (Sale/Rent):", listingTypeTextField);
        addTextFieldWithLabel("Price:", priceTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProperty());
        add(saveButton);

        // Display the form
        setVisible(true);
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


    private void saveProperty() {
        // Get values from the form
        String streetAddress = streetAddressTextField.getText();
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        String cityName = cityNameTextField.getText();
        int developerLicenseID = Integer.parseInt(developerLicenseIDTextField.getText());
        int strataID = Integer.parseInt(strataIDTextField.getText());
        String phone = phoneTextField.getText();
        int bedrooms = Integer.parseInt(bedroomsTextField.getText());
        int bathrooms = Integer.parseInt(bathroomsTextField.getText());
        int sizeInSqft = Integer.parseInt(sizeInSqftTextField.getText());
        int hasAC = hasACCheckBox.isSelected() ? 1 : 0;
        String listingType = listingTypeTextField.getText();
        int price = Integer.parseInt(priceTextField.getText());

        // Create a Property object
        EntityModel property = new Property(
                streetAddress, selectedProvince, cityName,
                developerLicenseID, strataID, phone,
                bedrooms, bathrooms, sizeInSqft, hasAC
        );

        int largestListingId = 1;
        EntityModel listing = new Listing(
                largestListingId, // Set the listingID to null or any appropriate value
                streetAddress,
                selectedProvince,
                cityName,
                ListingType.fromLabel(listingType), // Set the ListingType to null or any appropriate value
                price, // Set the price to null or any appropriate value
                1// Set the active to null or any appropriate value
        );

        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(property);
        databaseConnectionHandler.insertData(listing);


        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(property.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PropertyForm());
    }
}
