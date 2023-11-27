package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Maintains;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;

public class MaintainsForm extends JFrame {
    private JTextField contractorIdTextField;
    private JTextField streetAddressTextField;
    private JComboBox<Province> provinceComboBox;
    private JTextField cityNameTextField;
    private JTextField areaOfResponsibilityTextField;

    public MaintainsForm() {
        // Set up the JFrame
        setTitle("Maintains Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        // Create and add components
        add(new JLabel("Contractor ID:"));
        contractorIdTextField = new JTextField();
        add(contractorIdTextField);

        add(new JLabel("Street Address:"));
        streetAddressTextField = new JTextField();
        add(streetAddressTextField);

        add(new JLabel("Province:"));
        provinceComboBox = new JComboBox<>(Province.values());
        add(provinceComboBox);

        add(new JLabel("City Name:"));
        cityNameTextField = new JTextField();
        add(cityNameTextField);

        add(new JLabel("Area of Responsibility:"));
        areaOfResponsibilityTextField = new JTextField();
        add(areaOfResponsibilityTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMaintains());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveMaintains() {
        // Get values from the form
        int contractorId = Integer.parseInt(contractorIdTextField.getText());
        String streetAddress = streetAddressTextField.getText();
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        String cityName = cityNameTextField.getText();
        String areaOfResponsibility = areaOfResponsibilityTextField.getText();

        // Create a Maintains object
        EntityModel maintains = new Maintains(contractorId, streetAddress, selectedProvince, cityName, areaOfResponsibility);
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(maintains);
        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(maintains.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MaintainsForm());
    }
}
