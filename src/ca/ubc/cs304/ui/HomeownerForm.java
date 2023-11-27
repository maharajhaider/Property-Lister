package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Homeowner;

import javax.swing.*;
import java.awt.*;

public class HomeownerForm extends JFrame {
    private JTextField phoneTextField;

    public HomeownerForm() {
        // Set up the JFrame
        setTitle("Homeowner Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(2, 2));

        // Create and add components
        add(new JLabel("Phone:"));
        phoneTextField = new JTextField();
        add(phoneTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveHomeowner());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveHomeowner() {
        // Get value from the form
        String phone = phoneTextField.getText();

        // Create a Homeowner object
        EntityModel homeowner = new Homeowner(phone);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(homeowner);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeownerForm());
    }
}
