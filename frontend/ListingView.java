package frontend;
// package frontend.target.classes;
import javax.swing.*;
import java.awt.*;

public class ListingView extends JFrame {
    private JLabel nameLabel, priceLabel, locationLabel;
    private JButton backButton;

    public ListingView(String name, String price, String location) {
        // Set up the frame
        setTitle("Listing View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        nameLabel = createStyledLabel("ID: " + name);
        priceLabel = createStyledLabel("Price: " + price);
        locationLabel = createStyledLabel("Type(sale/rent): " + location);
        backButton = createStyledButton("Back");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add components to the frame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridy = 1;
        add(priceLabel, gbc);

        gbc.gridy = 2;
        add(locationLabel, gbc);

        gbc.gridy = 3;
        add(backButton, gbc);

        // Add action listener for the back button
        backButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Going back to the Search UI");
            setVisible(false);
            dispose();
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 28));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 102, 204)); // Dark blue background
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false); // Remove focus border for better appearance
        return button;
    }

    public void display() {
        getContentPane().setBackground(new Color(51, 153, 255)); // Light blue background
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListingView listingView = new ListingView("Sample Listing", "$500", "City Center");
            listingView.display();
        });
    }
}
