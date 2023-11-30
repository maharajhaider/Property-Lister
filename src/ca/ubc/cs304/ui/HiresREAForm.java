package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.HiresREA;

import javax.swing.*;
import java.awt.*;

public class HiresREAForm extends JFrame {
    private JTextField homeownerPhoneTextField;
    private JTextField realEstateAgentPhoneTextField;


    public HiresREAForm(String phone,DatabaseConnectionHandler databaseConnectionHandler) {
        // Set up the JFrame
        setTitle("Hires REA Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
        add(new JLabel("Homeowner Phone:"));
        homeownerPhoneTextField = new JTextField();
        add(homeownerPhoneTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveHiresREA(phone,databaseConnectionHandler));
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    public String validate(String input) {
        if (input == null || (!input.contains(".") && !input.contains("@"))) {
            return input;
        } else {
            return null; // Invalid string
        }
    }


    private void saveHiresREA(String realEstateAgentPhone,DatabaseConnectionHandler databaseConnectionHandler) {
        // Get values from the form
        String homeownerPhone = validate(homeownerPhoneTextField.getText());

        if(homeownerPhone == null){
            JOptionPane.showMessageDialog(this, "please do not use . or @");
            return;
        }

        // Create a HiresREA object
        EntityModel hiresREA = new HiresREA(homeownerPhone, realEstateAgentPhone);
//        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(hiresREA, null);
    }


}
