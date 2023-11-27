package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.HiresContractor;

import javax.swing.*;
import java.awt.*;

public class HiresContractorForm extends JFrame {
    private JTextField homeownerPhoneTextField;
    private JTextField contractorIdTextField;

    public HiresContractorForm() {
        // Set up the JFrame
        setTitle("Hires Contractor Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
        add(new JLabel("Homeowner Phone:"));
        homeownerPhoneTextField = new JTextField();
        add(homeownerPhoneTextField);

        add(new JLabel("Contractor ID:"));
        contractorIdTextField = new JTextField();
        add(contractorIdTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveHiresContractor());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveHiresContractor() {
        // Get values from the form
        String homeownerPhone = homeownerPhoneTextField.getText();
        int contractorId = Integer.parseInt(contractorIdTextField.getText());

        // Create a HiresContractor object
        EntityModel hiresContractor = new HiresContractor(homeownerPhone, contractorId);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(hiresContractor);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(hiresContractor.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HiresContractorForm());
    }
}
