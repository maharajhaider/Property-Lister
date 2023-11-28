package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Listing;

import javax.swing.*;
import java.awt.*;

public class SearchResultScreen extends JFrame {

    public SearchResultScreen(Listing[] results, DatabaseConnectionHandler databaseConnectionHandler) {

        // Set the layout manager for the JFrame
        setLayout(new BorderLayout());

        DefaultListModel<Listing> resultList = new DefaultListModel<>();
        for (Listing result : results) {
            resultList.addElement(result);
        }

        JList<Listing> resultJList = new JList<>(resultList);
        resultJList.setCellRenderer(new IndividualListingRenderer(databaseConnectionHandler));

        // Create a scroll pane for the JList
        JScrollPane scrollPane = new JScrollPane(resultJList);

        // Add the scroll pane to the center of the JFrame
        add(scrollPane, BorderLayout.CENTER);

        // Set JFrame properties
        setTitle("Search Results");
        setSize(400, 300); // Adjust the size as needed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window on exit
        setLocationRelativeTo(null); // Center the JFrame
        setVisible(true);
    }
}
