package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.Listing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IndividualListingRenderer extends JPanel implements ListCellRenderer<Listing> {
    private JLabel listingIDLabel;
    private JLabel addressLabel;
    private JButton viewButton;
    private JButton editButton;
    private JButton deleteButton;

    private DatabaseConnectionHandler databaseConnectionHandler;

    public IndividualListingRenderer(DatabaseConnectionHandler databaseConnectionHandler) {
        setLayout(new BorderLayout());

        this.databaseConnectionHandler = databaseConnectionHandler;

        listingIDLabel = new JLabel();
        addressLabel = new JLabel();
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        viewButton.addActionListener(new ViewButtonActionListener());
        editButton.addActionListener(new EditButtonActionListener());
        deleteButton.addActionListener(new DeleteButtonActionListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(listingIDLabel, BorderLayout.NORTH);
        add(addressLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Listing> list, Listing value, int index, boolean isSelected, boolean cellHasFocus) {
        listingIDLabel.setText(value.listingID().toString());
        addressLabel.setText(value.streetAddress());
        return this;
    }

    private class ViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "View button clicked");
        }
    }

    private class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Edit button clicked");
        }
    }

    private class DeleteButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            databaseConnectionHandler.deleteListing(Integer.parseInt(listingIDLabel.getText()));
        }
    }

}
