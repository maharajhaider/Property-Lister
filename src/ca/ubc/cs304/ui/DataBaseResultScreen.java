package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.EntityModel;
import ca.ubc.cs304.model.entity.Listing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DataBaseResultScreen extends JFrame {

    DatabaseConnectionHandler databaseConnectionHandler;
    private JTable table;

    public DataBaseResultScreen(String tableName, List<String> selectedAttributes, DatabaseConnectionHandler databaseConnectionHandler) {
        this.databaseConnectionHandler = databaseConnectionHandler;
        setTitle("Database Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // projection data
        List<EntityModel> projectionResult = databaseConnectionHandler.projectData(tableName,selectedAttributes);

        // Create a 2D array for data
        String[][] data = new String[projectionResult.size()][selectedAttributes.size()];

        // Fill the data array
        for (int i = 0; i < projectionResult.size(); i++) {
            Object obj = projectionResult.get(i);
            for (int j = 0; j < selectedAttributes.size(); j++) {
                data[i][j] = getObjectValue(obj, selectedAttributes.get(j));
            }
        }

        // Create JTable with the data and column names
        table = new JTable(data, selectedAttributes.toArray());

        // Add table to a scroll pane for better visibility
        JScrollPane scrollPane = new JScrollPane(table);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String getObjectValue(Object obj, String s) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(s);
            field.setAccessible(true);
            return field.get(obj).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
