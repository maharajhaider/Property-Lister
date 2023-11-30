package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.Listing;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;

public class EditListingScreen extends JFrame {

    private JCheckBox activeCheckBox;
    private JTextField listingTypeTextField;
    private JTextField priceTextField;

    private DatabaseConnectionHandler databaseConnectionHandler;



    public EditListingScreen(DatabaseConnectionHandler databaseConnectionHandler, int listingID) {
        this.databaseConnectionHandler = databaseConnectionHandler;
        // Set up the JFrame
        setTitle("Edit Listing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600); // Increased the height to accommodate more fields
        setLayout(new GridLayout(14, 2, 10, 10)); // Added spacing between rows and columns

        // Create and add components with labels

        addCheckBoxWithLabel("Active:");
        addTextFieldWithLabel("Listing Type (Sale/Rent):", listingTypeTextField);
        addTextFieldWithLabel("Price:", priceTextField);

        JButton saveButton = new JButton("Edit");
        saveButton.addActionListener(e -> {
            editProperty(listingID);
            setVisible(false);
            dispose();
        });
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

    private void addCheckBoxWithLabel(String labelText) {
        add(createLabel(labelText));
        JCheckBox checkBox = new JCheckBox();
        add(checkBox);
        activeCheckBox = checkBox;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }


    private void editProperty(int listingID) {

        int active = activeCheckBox.isSelected() ? 1 : 0;
        String listingTypeString = listingTypeTextField.getText();
        int price = Integer.parseInt(priceTextField.getText());
        ListingType listingType;
        if (listingTypeString.equalsIgnoreCase("sale")) {
            listingType = ListingType.SALE;
        } else {
            listingType = ListingType.RENT;
        }
        System.out.println(databaseConnectionHandler.updateListing(active,listingType,price, listingID));
    }
}
