package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.ContractorCompany;
import ca.ubc.cs304.model.HasID;
import ca.ubc.cs304.model.enums.ChargeSchedule;

import javax.swing.*;
import java.awt.*;

public class ContractorCompanyForm extends JFrame {
    private JTextField nameTextField;
    private JTextField contractorIdTextField;
    private JComboBox<ChargeSchedule> chargeScheduleComboBox;

    public ContractorCompanyForm() {
        // Set up the JFrame
        setTitle("Contractor Company Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        // Create and add components
//        add(new JLabel("Contractor ID:"));
//        contractorIdTextField = new JTextField();
//        add(contractorIdTextField);

        add(new JLabel("Name:"));
        nameTextField = new JTextField();
        add(nameTextField);

        add(new JLabel("Charge Schedule:"));
        chargeScheduleComboBox = new JComboBox<>(ChargeSchedule.values());
        add(chargeScheduleComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveContractorCompany());
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
    private void saveContractorCompany() {
        // Get values from the form
        String companyName = validate(nameTextField.getText());
        ChargeSchedule selectedChargeSchedule = (ChargeSchedule) chargeScheduleComboBox.getSelectedItem();


        if(companyName == null){
            JOptionPane.showMessageDialog(this, "please do not use . or @");
            return;
        }

        // Create a ContractorCompany object
        HasID contractorCompany = new ContractorCompany(null, companyName, selectedChargeSchedule);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        int contractorId = databaseConnectionHandler.generateId(contractorCompany);
        databaseConnectionHandler.insertData(contractorCompany, contractorId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContractorCompanyForm());
    }
}