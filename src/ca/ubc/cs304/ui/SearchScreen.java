package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.Listing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchScreen extends JFrame {
    private Image backgroundImage;
    private JPanel backgroundPanel;
    private JTextField searchBar;
    private JTextField listingIDTextField;
    private JComboBox<String> typeComboBox;
    private JTextField priceText;
    private JCheckBox activeCheckBox;
    private JComboBox<String> operatorComboBoxSearchBar;
    private JComboBox<String> operatorComboBoxListingId;
    private JComboBox<String> operatorComboBoxType;
    private JComboBox<String> operatorComboBoxPrice;
    private JComboBox<String> operatorCheckBoxActive;
    private List<String> queryParameters;



    private DatabaseConnectionHandler databaseConnectionHandler;

    public SearchScreen() throws IOException {
        databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.login("ora_mhaider0", "a94579901");
        databaseConnectionHandler.databaseSetup();
        queryParameters = new ArrayList<>();
        //background
        backgroundImage = ImageIO.read(new File("resources/SearchScreenBackground.png"));
        setLayout(new BorderLayout());
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };



        // search text bar
        searchBar = new JTextField("Search Listing with address");

        searchBar.setPreferredSize(new Dimension(250, 30));


        // search button
        JButton searchButton = new JButton("Search Listing");
        searchButton.addActionListener(e -> {
            queryParameters.clear();
            queryParameters.add((String) operatorComboBoxSearchBar.getSelectedItem());
            queryParameters.add(searchBar.getText());

            queryParameters.add((String) operatorComboBoxListingId.getSelectedItem());
            queryParameters.add(listingIDTextField.getText() );

            queryParameters.add((String) operatorCheckBoxActive.getSelectedItem());
            queryParameters.add(String.valueOf(activeCheckBox.isSelected()));

            queryParameters.add((String) operatorComboBoxPrice.getSelectedItem());
            queryParameters.add(String.valueOf(priceText.getText()));

            queryParameters.add((String) operatorComboBoxType.getSelectedItem());
            queryParameters.add((String) typeComboBox.getSelectedItem());

            List<Listing> resultListings = databaseConnectionHandler.getListings(queryParameters);
            new SearchResultScreen(resultListings,databaseConnectionHandler);
        });

        JButton viewDatabaseButton = new JButton("Go to search data page");
        viewDatabaseButton.addActionListener(e -> {

            new ViewDatabaseInfoScreen(databaseConnectionHandler).setVisible(true);
        });

        listingIDTextField = new JTextField("Enter listing id");
        String [] type = {"sale", "rent"};
        typeComboBox = new JComboBox<>(type);
        priceText = new JTextField("Enter price");
        activeCheckBox = new JCheckBox("Active?");
        String[] operators = {"disable filter ->","AND", "OR"};
        String[] searchBarOperators = {"disable filter ->", "enable filter"};
        operatorComboBoxSearchBar = new JComboBox<>(searchBarOperators);
        operatorComboBoxListingId = new JComboBox<>(operators);
        operatorComboBoxType = new JComboBox<>(operators);
        operatorComboBoxPrice = new JComboBox<>(operators);
        operatorCheckBoxActive = new JComboBox<>(operators);






        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        backgroundPanel.add(operatorComboBoxSearchBar);
        backgroundPanel.add(searchBar );
        backgroundPanel.add(operatorComboBoxListingId);
        backgroundPanel.add(listingIDTextField);
        backgroundPanel.add(operatorComboBoxType);
        backgroundPanel.add(typeComboBox);
        backgroundPanel.add(operatorComboBoxPrice);
        backgroundPanel.add(priceText);
        backgroundPanel.add(operatorCheckBoxActive);
        backgroundPanel.add(activeCheckBox);
        backgroundPanel.add(searchButton);
        backgroundPanel.add(viewDatabaseButton);

        add(backgroundPanel);

        setTitle("Search Listing");
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
