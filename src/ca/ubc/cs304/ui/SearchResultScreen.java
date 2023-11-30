package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.Listing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SearchResultScreen extends JFrame {
    JList<ButtonItem> resultJList;

    public SearchResultScreen(List<Listing> results, DatabaseConnectionHandler databaseConnectionHandler) {

        // Set the layout manager for the JFrame
        setLayout(new BorderLayout());

        DefaultListModel<ButtonItem> resultList = new DefaultListModel<>();
        for (Listing result : results) {
            resultList.addElement(new ButtonItem(result));
        }

        resultJList = new JList<>(resultList);

        resultJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        resultJList.setCellRenderer(new ButtonListRenderer());

        resultJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                clickButtonAt(event.getPoint());
            }
        });

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
        setEnabled(true);
    }

    private void clickButtonAt(Point point) {
        int index = resultJList.locationToIndex(point);
        ButtonItem item = resultJList.getModel().getElementAt(index);
        item.getViewButton().doClick();
    }

    public class ButtonItem {
        private JButton viewButton;
        private Listing listing;

        public ButtonItem(Listing listing) {
            this.listing = listing;

            viewButton = new JButton("View");
            viewButton.addActionListener(new ButtonActionListener("View"));
        }

        public JButton getViewButton() {
            return viewButton;
        }

        @Override
        public String toString() {
            return listing.toString();
        }

        private class ButtonActionListener implements ActionListener {
            private String action;

            public ButtonActionListener(String action) {
                this.action = action;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("View clicked");
                new ListingView(listing).setVisible(true);
            }
        }
    }

    class ButtonListRenderer implements ListCellRenderer<ButtonItem> {

        @Override
        public Component getListCellRendererComponent(JList<? extends ButtonItem> comp, ButtonItem value, int index,
                                                      boolean isSelected, boolean hasFocus) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel listingID = new JLabel("ID: " +value.listing.listingId().toString() + "|");
            JLabel listingAddress = new JLabel("Address: " +value.listing.streetAddress() + "|");
            JLabel listingPrice = new JLabel("Price: " +value.listing.price().toString() + "|");
            panel.add(listingID);
            panel.add(listingAddress);
            panel.add(listingPrice);
            panel.add(value.getViewButton());
            if (isSelected) {
                panel.setBackground(comp.getSelectionBackground());
                panel.setForeground(comp.getSelectionForeground());
            } else {
                panel.setBackground(comp.getBackground());
                panel.setForeground(comp.getForeground());
            }

            return panel;
        }
    }
}
