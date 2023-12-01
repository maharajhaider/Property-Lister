package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.City;
import ca.ubc.cs304.model.entity.EntityModel;
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
    public String validate(String input) {
        if (input == null || (!input.contains(".") && !input.contains("@"))) {
            return input;
        } else {
            return null; // Invalid string
        }
    }
    private void saveCity() {
        // Get values from the form
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        String cityName = validate(nameTextField.getText());
        double taxRate = validateInt(Double.parseDouble(taxRateTextField.getText()));

        if(cityName == null){
            JOptionPane.showMessageDialog(this, "please do not use . or @");
            return;
        }

        if(taxRate == -1){
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your input fields.");
            return;
        }

        // Create a City object
        EntityModel city = new City(selectedProvince, cityName, taxRate);

        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(city, null);

        JOptionPane.showMessageDialog(this, "Success");
    }

    private double validateInt(double v) {

        if(v >=0){
            return 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CityForm());
    }
}
