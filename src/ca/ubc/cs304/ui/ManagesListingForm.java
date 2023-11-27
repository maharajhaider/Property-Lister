package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.ManagesListing;

import javax.swing.*;
import java.awt.*;

public class ManagesListingForm extends JFrame {
    private JTextField realEstateAgentPhoneTextField;
    private JTextField listingIdTextField;

    public ManagesListingForm() {
        // Set up the JFrame
        setTitle("Manages Listing Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
        add(new JLabel("Real Estate Agent Phone:"));
        realEstateAgentPhoneTextField = new JTextField();
        add(realEstateAgentPhoneTextField);

        add(new JLabel("Listing ID:"));
        listingIdTextField = new JTextField();
        add(listingIdTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveManagesListing());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveManagesListing() {
        // Get values from the form
        String realEstateAgentPhone = realEstateAgentPhoneTextField.getText();
        int listingId = Integer.parseInt(listingIdTextField.getText());

        // Create a ManagesListing object
        EntityModel managesListing = new ManagesListing(realEstateAgentPhone, listingId);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(managesListing);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagesListingForm());
    }
}
