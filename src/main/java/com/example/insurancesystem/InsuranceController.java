package com.example.insurancesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.*;

public class InsuranceController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //For landing page
    @FXML
    private Button newPolicyButton;
    @FXML
    private Button policyHolderInfobutton;
    @FXML
    private Button policyInfoButton;
    @FXML
    private Button newProductButton;
    @FXML
    private Button backButton;

    //For adding policy holder page
    @FXML
    private TextField omangTextField;
    @FXML
    private TextField fNameTextField;
    @FXML
    private TextField lNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Button submitPHButton;

    //For adding product page
    @FXML
    private TextField productIDTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productTypeTextField;
    @FXML
    private TextField policyNoTextField;
    @FXML
    private TextField areaTextField;
    @FXML
    private Button submitProductInfoButton;
    @FXML
    private Button goBackButton;

    //Redirects user to policy holder entry screen when button clicked
    public void toPolicyHolderEntry(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("policyHolderEntry.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toProductEntry(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("productEntry.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Redirects user to landing page
    public void toLandingPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //function to submit policy holder info into DB
    public void submitPHInfo(ActionEvent a){
        if(omangTextField.getText().isBlank()==false && fNameTextField.getText().isBlank()==false && lNameTextField.getText().isBlank()==false && emailTextField.getText().isBlank()==false && addressTextField.getText().isBlank()==false){

            String omang = this.omangTextField.getText().toString();
            String fName = this.fNameTextField.getText().toString();
            String lName = this.lNameTextField.getText().toString();
            String email = this.emailTextField.getText().toString();
            String address = this.addressTextField.getText().toString();

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String insertData = "INSERT INTO `T_PolicyHolder` (`Omang`, `FName`, `LName`, `Email`, `BranchID`, `Address`) " + "VALUES ('"+omang+"', '"+fName+"', '"+lName+"', '"+email+"', '12345', '"+address+"');";

            try{
                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate(insertData);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.printf("Error inserting policy holder info!");
        }
    }

    public void submitProductInfo(ActionEvent a){
        if(productIDTextField.getText().isBlank()==false && productNameTextField.getText().isBlank()==false && productTypeTextField.getText().isBlank()==false && policyNoTextField.getText().isBlank()==false && areaTextField.getText().isBlank()==false){

            String productID = this.productIDTextField.getText().toString();
            String productName = this.productNameTextField.getText().toString();
            String productType = this.productTypeTextField.getText().toString();
            String policyNumber = this.policyNoTextField.getText().toString();
            String areaName = this.areaTextField.getText().toString();

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String insertData = "INSERT INTO `T_Product` (`ProductID`, `ProductName`, `Type`, `PolicyNo`, `AName`) " + "VALUES ('"+productID+"', '"+productName+"', '"+productType+"', '"+policyNumber+"', '"+areaName+"');";

            try{
                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate(insertData);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.printf("Error inserting product info!");
        }
    }

}