package ca.ubc.cs304.ui;

import ca.ubc.cs304.model.ContractorRange;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContractorRangeGUI extends JFrame {

    private JTable contractorTable;

    public ContractorRangeGUI(List<ContractorRange> contractorRanges) {
        setTitle("Wide Ranged Contractors");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI(contractorRanges);
    }

    private void initUI(List<ContractorRange> contractorRanges) {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Contractor ID", "Contractor Name", "Province", "Cities Involved"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        contractorRanges.forEach(contractorRange -> {
            model.addRow(new Object[]{
                    contractorRange.contractorId(),
                    contractorRange.contractorName(),
                    contractorRange.involvementByProvince().keySet(),
                    contractorRange.involvementByProvince().values()
            });
        });

        contractorTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(contractorTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }


    // Replace this method with the actual call to findWideRangedContractors

}
