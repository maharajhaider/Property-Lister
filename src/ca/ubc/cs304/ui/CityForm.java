package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.City;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;

public class CityForm extends JFrame {
    private JTextField nameTextField;
    private JTextField taxRateTextField;
    private JComboBox<Province> provinceComboBox;

    public CityForm() {
        // Set up the JFrame
        setTitle("City Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));

        // Create and add components
        add(new JLabel("Province:"));
        provinceComboBox = new JComboBox<>(Province.values());
        add(provinceComboBox);

        add(new JLabel("Name:"));
        nameTextField = new JTextField();
        add(nameTextField);

        add(new JLabel("Tax Rate:"));
        taxRateTextField = new JTextField();
        add(taxRateTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveCity());
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    private void saveCity() {
        // Get values from the form
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        String cityName = nameTextField.getText();
        double taxRate = Double.parseDouble(taxRateTextField.getText());

        // Create a City object
        EntityModel city = new City(selectedProvince, cityName, taxRate);

        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
//        databaseConnectionHandler.login("ora_bansal21","a67617654");
        databaseConnectionHandler.insertData(city);

        // Print the insert statement (you can replace this with your database interaction)
        System.out.println(city.insertStatement());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CityForm());
    }
}
