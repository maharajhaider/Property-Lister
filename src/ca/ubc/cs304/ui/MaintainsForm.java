package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Maintains;
import ca.ubc.cs304.model.enums.Province;



public class MaintainsForm{


    public MaintainsForm(String streetAddress,int contractorID,Province province,String cityName,String areaofResponsibilty,DatabaseConnectionHandler databaseConnectionHandler){
        saveMaintains(streetAddress,contractorID,province,cityName,areaofResponsibilty,databaseConnectionHandler);
    }

//    public MaintainsForm() {
//        // Set up the JFrame
//        setTitle("Maintains Form");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(400, 300);
//        setLayout(new GridLayout(6, 2));
//
//        // Create and add components
//        add(new JLabel("Contractor ID:"));
//        contractorIdTextField = new JTextField();
//        add(contractorIdTextField);
//
//        add(new JLabel("Street Address:"));
//        streetAddressTextField = new JTextField();
//        add(streetAddressTextField);
//
//        add(new JLabel("Province:"));
//        provinceComboBox = new JComboBox<>(Province.values());
//        add(provinceComboBox);
//
//        add(new JLabel("City Name:"));
//        cityNameTextField = new JTextField();
//        add(cityNameTextField);
//
//        add(new JLabel("Area of Responsibility:"));
//        areaOfResponsibilityTextField = new JTextField();
//        add(areaOfResponsibilityTextField);
//
//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> saveMaintains());
//        add(saveButton);
//
//        // Display the form
//        setVisible(true);
//    }

    private void saveMaintains(String streetAddress,int contractorId,Province selectedProvince,String cityName,String areaOfResponsibility,DatabaseConnectionHandler databaseConnectionHandler) {
        // Get values from the form
//        int contractorId = Integer.parseInt(contractorIdTextField.getText());
//        String streetAddress = streetAddressTextField.getText();
//        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
//        String cityName = cityNameTextField.getText();
//        String areaOfResponsibility = areaOfResponsibilityTextField.getText();

        // Create a Maintains object
        EntityModel maintains = new Maintains(contractorId, streetAddress, selectedProvince, cityName, areaOfResponsibility);
//        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(maintains, null);
    }


}
