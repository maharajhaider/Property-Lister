package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.Listing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearchScreen extends JFrame {
    private Image backgroundImage;
    private JPanel backgroundPanel;
    private JTextField searchBar;

    private DatabaseConnectionHandler databaseConnectionHandler;

    public SearchScreen() throws IOException {
        databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.login("ora_mhaider0", "a94579901");
        databaseConnectionHandler.databaseSetup();
        //background
        backgroundImage = ImageIO.read(new File("resources/SearchScreenBackground.png"));
        setLayout(new BorderLayout());
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // search bar
        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(200, 30));

        // search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String searchText = searchBar.getText();
            List<Listing> resultListings = databaseConnectionHandler.getListings(searchText);
            new SearchResultScreen(resultListings,databaseConnectionHandler);
        });


        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backgroundPanel.add(searchBar);
        backgroundPanel.add(searchButton);

        add(backgroundPanel);

        setTitle("Listing site");
        setSize(1430, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the JFrame
        setVisible(true);
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SearchScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
