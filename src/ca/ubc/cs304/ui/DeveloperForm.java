package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Developer;
import ca.ubc.cs304.model.HasID;

import javax.swing.*;
import java.awt.*;

public class DeveloperForm extends JFrame {
    private JTextField nameTextField;
    private JTextField licenseIdTextField;

    public DeveloperForm() {
        // Set up the JFrame
        setTitle("Developer Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
//        add(new JLabel("Developer License ID:"));
//        licenseIdTextField = new JTextField();
//        add(licenseIdTextField);

        add(new JLabel("Name:"));
        nameTextField = new JTextField();
        add(nameTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveDeveloper());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveDeveloper() {
        // Get values from the form
        String developerName = nameTextField.getText();

        // Create a Developer object
        HasID developer = new Developer(null, developerName);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        int developerLicenseId = databaseConnectionHandler.generateId(developer);
        databaseConnectionHandler.insertData(developer, developerLicenseId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeveloperForm());
    }
}
