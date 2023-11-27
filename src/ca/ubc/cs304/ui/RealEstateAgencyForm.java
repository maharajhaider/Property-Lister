package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.RealEstateAgency;

import javax.swing.*;
import java.awt.*;

public class RealEstateAgencyForm extends JFrame {
    private JTextField agencyIdTextField;
    private JTextField nameTextField;
    private JTextField ratingTextField;

    public RealEstateAgencyForm() {
        // Set up the JFrame
        setTitle("Real Estate Agency Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2, 10, 10)); // Added spacing between rows and columns

        // Create and add components with labels
        addTextFieldWithLabel("Agency ID:", agencyIdTextField);
        addTextFieldWithLabel("Name:", nameTextField);
        addTextFieldWithLabel("Rating:", ratingTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveRealEstateAgency());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void addTextFieldWithLabel(String labelText, JTextField textField) {
        add(createLabel(labelText));
        textField = new JTextField();  // <-- Remove this line
        textField.setPreferredSize(new Dimension(200, 30));
        add(textField);

        // Assign the text field to the corresponding instance variable
        if (labelText.equals("Agency ID:")) {
            agencyIdTextField = textField;
        } else if (labelText.equals("Name:")) {
            nameTextField = textField;
        } else if (labelText.equals("Rating:")) {
            ratingTextField = textField;
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    private void saveRealEstateAgency() {
        // Get values from the form
        int agencyId = Integer.parseInt(agencyIdTextField.getText());
        String name = nameTextField.getText();
        double rating = Double.parseDouble(ratingTextField.getText());

        // Create a RealEstateAgency object
        EntityModel realEstateAgency = new RealEstateAgency(agencyId, name, rating);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(realEstateAgency);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(realEstateAgency.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RealEstateAgencyForm());
    }
}
