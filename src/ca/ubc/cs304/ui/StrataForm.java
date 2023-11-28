package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.HasID;
import ca.ubc.cs304.model.Strata;

import javax.swing.*;
import java.awt.*;

public class StrataForm extends JFrame {
    private JTextField strataIdTextField;
    private JTextField nameTextField;
    private JLabel validationLabel;

    public StrataForm() {
        // Set up the JFrame
        setTitle("Strata Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLayout(new GridLayout(3, 2, 10, 10)); // Added spacing between rows and columns

        // Create and add components with labels
        addTextFieldWithLabel("Strata ID:", strataIdTextField);
        addTextFieldWithLabel("Name:", nameTextField);

        validationLabel = new JLabel("");
        validationLabel.setForeground(Color.RED); // Set the text color to red for better visibility
        add(validationLabel);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveStrata());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void addTextFieldWithLabel(String labelText, JTextField textField) {
        add(createLabel(labelText));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        add(textField);

        // Assign the text field to the corresponding instance variable
        switch (labelText) {
            case "Strata ID:":
                strataIdTextField = textField;
                break;
            case "Name:":
                nameTextField = textField;
                break;
            default:
                break;
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    private void saveStrata() {
        String name = nameTextField.getText();

        // Create a Strata object
        HasID strata = new Strata(null, name);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        int strataId = databaseConnectionHandler.generateId(strata);
        databaseConnectionHandler.insertData(strata, strataId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StrataForm());
    }
}
