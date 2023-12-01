package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.AgencyInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReputableAgenciesUI extends JFrame {
   ;

    private JTable table;
    private DefaultTableModel tableModel;

    public ReputableAgenciesUI(DatabaseConnectionHandler databaseConnectionHandler) {
        super("Reputable Agencies");


        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        setupUI();
        loadReputableAgenciesData(databaseConnectionHandler);

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

    private void loadReputableAgenciesData(DatabaseConnectionHandler databaseConnectionHandler) {
        List<AgencyInfo> reputableAgencies = databaseConnectionHandler.getReputableAgencies();


        String[] columns = {"Agency ID", "Agency Name", "Rating", "Agent Count", "Avg Experience"};
        tableModel.setColumnIdentifiers(columns);

        for (AgencyInfo agency : reputableAgencies) {
            Object[] rowData = {
                    agency.agencyId(),
                    agency.name(),
                    agency.rating(),
                    agency.numAgents(),
                    agency.avgAgentExp()
            };
            tableModel.addRow(rowData);
        }
    }


}
