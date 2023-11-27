package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.HiresREA;

import javax.swing.*;
import java.awt.*;

public class HiresREAForm extends JFrame {
    private JTextField homeownerPhoneTextField;
    private JTextField realEstateAgentPhoneTextField;

    public HiresREAForm() {
        // Set up the JFrame
        setTitle("Hires REA Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
        add(new JLabel("Homeowner Phone:"));
        homeownerPhoneTextField = new JTextField();
        add(homeownerPhoneTextField);

        add(new JLabel("Real Estate Agent Phone:"));
        realEstateAgentPhoneTextField = new JTextField();
        add(realEstateAgentPhoneTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveHiresREA());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveHiresREA() {
        // Get values from the form
        String homeownerPhone = homeownerPhoneTextField.getText();
        String realEstateAgentPhone = realEstateAgentPhoneTextField.getText();

        // Create a HiresREA object
        EntityModel hiresREA = new HiresREA(homeownerPhone, realEstateAgentPhone);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(hiresREA);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(hiresREA.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HiresREAForm());
    }
}
