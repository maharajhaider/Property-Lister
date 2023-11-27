package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.RealEstateAgent;

import javax.swing.*;
import java.awt.*;

public class RealEstateAgentForm extends JFrame {
    private JTextField phoneTextField;
    private JTextField agentLicenseIdTextField;
    private JTextField yearsOfExpTextField;
    private JTextField agencyIdTextField;
    private JLabel validationLabel;

    public RealEstateAgentForm() {
        // Set up the JFrame
        setTitle("Real Estate Agent Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(5, 2, 10, 10)); // Added spacing between rows and columns

        // Create and add components with labels
        addTextFieldWithLabel("Phone:", phoneTextField);
        addTextFieldWithLabel("Agent License ID:", agentLicenseIdTextField);
        addTextFieldWithLabel("Years of Experience:", yearsOfExpTextField);
        addTextFieldWithLabel("Agency ID:", agencyIdTextField);

        validationLabel = new JLabel("");
        validationLabel.setForeground(Color.RED); // Set the text color to red for better visibility
        add(validationLabel);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveRealEstateAgent());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void addTextFieldWithLabel(String labelText, JTextField textField) {
        add(createLabel(labelText));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        add(textField);

        switch (labelText) {
            case "Phone:":
                phoneTextField = textField;
                break;
            case "Agent License ID:":
                agentLicenseIdTextField = textField;
                break;
            case "Years of Experience:":
                yearsOfExpTextField = textField;
                break;
            case "Agency ID:":
                agencyIdTextField = textField;
                break;
            default:
                break;
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    private void saveRealEstateAgent() {
        // Get values from the form
        String phone = phoneTextField.getText();
        int agentLicenseId = Integer.parseInt(agentLicenseIdTextField.getText());
        int yearsOfExp;
        int agencyId;

        try {
            yearsOfExp = Integer.parseInt(yearsOfExpTextField.getText());

            // Validate yearsOfExp
            if (yearsOfExp < 0) {
                validationLabel.setText("Years of Experience should be non-negative");
                return; // Stop processing if validation fails
            }

            agencyId = Integer.parseInt(agencyIdTextField.getText());

            // Validate agencyId
            if (agencyId < 0) {
                validationLabel.setText("Agency ID should be non-negative");
                return; // Stop processing if validation fails
            }

            // Clear validation message if validation passes
            validationLabel.setText("");

        } catch (NumberFormatException ex) {
            // Handle parsing error (non-numeric input for yearsOfExp or agencyId)
            validationLabel.setText("Invalid input format for Years of Experience or Agency ID");
            return; // Stop processing if validation fails
        }

        // Create a RealEstateAgent object
        EntityModel realEstateAgent = new RealEstateAgent(phone, agentLicenseId, yearsOfExp, agencyId);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(realEstateAgent);
        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(realEstateAgent.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RealEstateAgentForm());
    }
}
