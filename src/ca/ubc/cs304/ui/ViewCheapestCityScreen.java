package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.AgencyInfo;
import ca.ubc.cs304.model.CityRealEstatePrice;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewCheapestCityScreen extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewCheapestCityScreen(ListingType listingType, DatabaseConnectionHandler databaseConnectionHandler) {
        super("Cheapest city");

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        setupUI();
        loadPriceData(databaseConnectionHandler, listingType);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadPriceData(DatabaseConnectionHandler databaseConnectionHandler, ListingType listingType) {
        CityRealEstatePrice rePrice = databaseConnectionHandler.findCheapestCity(listingType);

        String[] columns = {"Province", "City", "Listing type", "Average price per sqft"};
        tableModel.setColumnIdentifiers(columns);


            Object[] rowData = {
                    rePrice.province(),
                    rePrice.cityName(),
                    rePrice.listingType(),
                    rePrice.avgPricePerSqft()
            };
            tableModel.addRow(rowData);

    }
}
