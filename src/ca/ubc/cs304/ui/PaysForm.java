package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.EntityModel;
import ca.ubc.cs304.model.entity.Pays;

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

    public String validate(String input) {
        if (input == null || (!input.contains(".") && !input.contains("@"))) {
            return input;
        } else {
            return null; // Invalid string
        }
    }

    public int validate1(int value){

        if(value >= 0){
            return 1;
        }else{
            return -1;
        }

    }

    private void savePays() {
        // Get values from the form
        String homeownerPhone = validate(homeownerPhoneTextField.getText());
        int strataId = validate1(Integer.parseInt(strataIdTextField.getText()));
        int fee = validate1(Integer.parseInt(feeTextField.getText()));

        if(homeownerPhone == null){
            JOptionPane.showMessageDialog(this, "please do not use . or @");
            return;
        }

        if(strataId == -1 || fee == -1){
            JOptionPane.showMessageDialog(this, "please make sure to have id/fee positive");
            return;
        }

        // Create a Pays object
        EntityModel pays = new Pays(homeownerPhone, strataId, fee);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(pays, null);
        JOptionPane.showMessageDialog(this, "Success");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaysForm());
    }
}
