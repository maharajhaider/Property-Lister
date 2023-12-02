package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.CityPropertyCount;
import ca.ubc.cs304.model.CityRealEstatePrice;
import ca.ubc.cs304.model.enums.ListingType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewPropertyByCityScreen extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewPropertyByCityScreen( DatabaseConnectionHandler databaseConnectionHandler) {
        super("Number of property in each city");

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        setupUI();
        loadPriceData(databaseConnectionHandler);

        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadPriceData(DatabaseConnectionHandler databaseConnectionHandler) {
        List<CityPropertyCount> cityPropertyCounts = databaseConnectionHandler.getPropertyCountByCity();

        String[] columns = {"Province", "City", "Number of properties"};
        tableModel.setColumnIdentifiers(columns);

        for (CityPropertyCount cpc: cityPropertyCounts) {
            Object[] rowData = {
                    cpc.province(),
                    cpc.cityName(),
                    cpc.properties()
            };
            tableModel.addRow(rowData);

        }




    }
}
