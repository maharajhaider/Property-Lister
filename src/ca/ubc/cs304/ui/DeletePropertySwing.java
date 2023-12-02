package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePropertySwing extends JFrame {
    private JTextField provinceField;
    private JTextField cityField;
    private JTextField addressField;
    private DatabaseConnectionHandler databaseConnectionHandler;

    public DeletePropertySwing(DatabaseConnectionHandler databaseConnectionHandler) {
        super("Delete Property");

        this.databaseConnectionHandler = databaseConnectionHandler;

        JLabel provinceLabel = new JLabel("Province:");
        JLabel cityLabel = new JLabel("City:");
        JLabel addressLabel = new JLabel("Street Address:");

        provinceField = new JTextField(20);
        cityField = new JTextField(20);
        addressField = new JTextField(20);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProperty();
            }
        });

        setLayout(new GridLayout(4, 2));
        add(provinceLabel);
        add(provinceField);
        add(cityLabel);
        add(cityField);
        add(addressLabel);
        add(addressField);
        add(new JLabel()); // Empty cell for spacing
        add(deleteButton);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the current frame
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void deleteProperty() {
        String province = provinceField.getText().trim();
        String city = cityField.getText().trim();
        String address = addressField.getText().trim();

        if (province.isEmpty() || city.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = databaseConnectionHandler.deleteProperty(Province.valueOf(province), city, address);

        if (success) {
            JOptionPane.showMessageDialog(this, "Property deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the current frame upon success
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting property.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
