package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.EntityModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewDatabaseInfoScreen extends JFrame{
    private JComboBox<String> selectTable;

    private JPanel checkboxesPanel;

    private JButton searchButton;
    DatabaseConnectionHandler databaseConnectionHandler;

    public ViewDatabaseInfoScreen(DatabaseConnectionHandler databaseConnectionHandler){
        this.databaseConnectionHandler = databaseConnectionHandler;
        checkboxesPanel = new JPanel();
        checkboxesPanel.setLayout(new FlowLayout());
        selectTable = new JComboBox<>();
        searchButton = new JButton("Search");
        selectTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCheckboxes(databaseConnectionHandler.getColumns((String) selectTable.getSelectedItem()));
                repaint();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                new DataBaseResultScreen((String) selectTable.getSelectedItem(),getSelectedAttributes(),databaseConnectionHandler);

            }
        });
        makeTableCombos();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(selectTable);
        add(checkboxesPanel);
        add(searchButton);

        setTitle("Search data");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the JFrame
        setVisible(true);


    }



    private void createCheckboxes(List<String> attributes) {
        checkboxesPanel.removeAll();
        for (String attribute : attributes) {
            JCheckBox checkBox = new JCheckBox(attribute);
            checkboxesPanel.add(checkBox);
        }
        checkboxesPanel.revalidate();
        checkboxesPanel.repaint();
    }

    private List<String> getSelectedAttributes() {
        List<String> selectedAttributes = new ArrayList<>();
        for (Component component : checkboxesPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    selectedAttributes.add(checkBox.getText());
                }
            }
        }
        return selectedAttributes;
    }


    private void makeTableCombos() {
        List<String> tables = databaseConnectionHandler.getTables();
        for (String table : tables) {
            selectTable.addItem(table);
        }

    }

}
