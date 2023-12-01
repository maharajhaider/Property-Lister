package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.EntityModel;
import ca.ubc.cs304.model.entity.HiresContractor;
import ca.ubc.cs304.model.enums.Province;

import javax.swing.*;
import java.awt.*;

public class HiresContractorForm extends JFrame {
    private JTextField homeownerPhoneTextField;
    private JTextField contractorIdTextField;

    private JTextField areaofResponsibilty;

//    public HiresContractorForm() {
//        // Set up the JFrame
//        setTitle("Hires Contractor Form");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(400, 200);
//        setLayout(new GridLayout(3, 2));
//
//        // Create and add components
//        add(new JLabel("Homeowner Phone:"));
//        homeownerPhoneTextField = new JTextField();
//        add(homeownerPhoneTextField);
//
//        add(new JLabel("Contractor ID:"));
//        contractorIdTextField = new JTextField();
//        add(contractorIdTextField);
//
//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> saveHiresContractor());
//        add(saveButton);
//
//        // Display the form
//        setVisible(true);
//    }

    String streetAddress;
    Province selectedProvince;
    String cityName;
    public HiresContractorForm(String streetAddress, Province selectedProvince,String cityName,int contractorID,DatabaseConnectionHandler databaseConnectionHandler) {
        // Set up the JFrame
        setTitle("Hires Contractor Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Create and add components
        add(new JLabel("Homeowner Phone:"));
        homeownerPhoneTextField = new JTextField();
        add(homeownerPhoneTextField);


        add(new JLabel("Area of Responsibilty:"));
        areaofResponsibilty= new JTextField();
        add(areaofResponsibilty);


        this.streetAddress = streetAddress;
        this.selectedProvince = selectedProvince;
        this.cityName = cityName;


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveHiresContractor(streetAddress,selectedProvince,cityName,contractorID,databaseConnectionHandler));
        add(saveButton);

        // Display the form
        setVisible(true);
    }

    public String validate(String input) {
        if (input == null || (!input.contains(".") && !input.contains("@"))) {
            return input;
        } else {
            return null; // Invalid string
        }
    }

    public int validateInt(int value){

        if(value >= 0){
            return 1;
        }else{
            return -1;
        }

    }

    private void saveHiresContractor(String streetAddress,Province selectedProvince,String cityName,int contractorId,DatabaseConnectionHandler databaseConnectionHandler) {
        // Get values from the form
        String homeownerPhone = validate(homeownerPhoneTextField.getText());
        String areaLabel = validate(areaofResponsibilty.getText());

        if(homeownerPhone == null || areaLabel == null){
            JOptionPane.showMessageDialog(this, "please do not use . or @");
            return;
        }


        new MaintainsForm(streetAddress,contractorId,selectedProvince,cityName,areaLabel,databaseConnectionHandler);


        EntityModel hiresContractor = new HiresContractor(homeownerPhone, contractorId);
//        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        databaseConnectionHandler.insertData(hiresContractor, null);
        JOptionPane.showMessageDialog(this, "Success");
    }



}
