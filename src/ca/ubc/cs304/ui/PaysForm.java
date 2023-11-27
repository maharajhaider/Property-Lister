package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Pays;

import javax.swing.*;
import java.awt.*;

public class PaysForm extends JFrame {
    private JTextField homeownerPhoneTextField;
    private JTextField strataIdTextField;
    private JTextField feeTextField;

    public PaysForm() {
        // Set up the JFrame
        setTitle("Pays Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        // Create and add components
        add(new JLabel("Homeowner Phone:"));
        homeownerPhoneTextField = new JTextField();
        add(homeownerPhoneTextField);

        add(new JLabel("Strata ID:"));
        strataIdTextField = new JTextField();
        add(strataIdTextField);

        add(new JLabel("Fee:"));
        feeTextField = new JTextField();
        add(feeTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> savePays());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void savePays() {
        // Get values from the form
        String homeownerPhone = homeownerPhoneTextField.getText();
        int strataId = Integer.parseInt(strataIdTextField.getText());
        int fee = Integer.parseInt(feeTextField.getText());

        // Create a Pays object
        EntityModel pays = new Pays(homeownerPhone, strataId, fee);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(pays);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(pays.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaysForm());
    }
}
