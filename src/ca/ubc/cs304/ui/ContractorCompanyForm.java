package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.ContractorCompany;
import ca.ubc.cs304.model.EntityModel;
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
        add(new JLabel("Contractor ID:"));
        contractorIdTextField = new JTextField();
        add(contractorIdTextField);

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

    private void saveContractorCompany() {
        // Get values from the form
        int contractorId = Integer.parseInt(contractorIdTextField.getText());
        String companyName = nameTextField.getText();
        ChargeSchedule selectedChargeSchedule = (ChargeSchedule) chargeScheduleComboBox.getSelectedItem();

        // Create a ContractorCompany object
        EntityModel contractorCompany = new ContractorCompany(contractorId, companyName, selectedChargeSchedule);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
//        databaseConnectionHandler.login("ora_bansal21","a67617654");
        databaseConnectionHandler.insertData(contractorCompany);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(contractorCompany.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContractorCompanyForm());
    }
}