package com.example.insurancesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private Label phPageOutputLabel;

    //For adding product page
    @FXML
    private TextField productIDTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productTypeTextField;
    @FXML
    private  ComboBox<String> policyNoComboBox;
    @FXML
    private ComboBox<String> areaNameComboBox;
    @FXML
    private Button submitProductInfoButton;
    @FXML
    private Button goBackButton;
    @FXML
    private Label productPageOutputLabel;

    //For policy info retrieval page
    @FXML
    private TextField omangNoRetrieval;
    @FXML
    private TextField claimNoRetrieval;
    @FXML
    private Button submitButtonRetrieval;
    @FXML
    private Button goBackRetrieval;
    @FXML
    private ListView<String> policyRetListView;
    @FXML
    private Label retPolicyOutputLabel;

    //For the policy holder and policy info retrieval page
    @FXML
    private ListView<String> pHolderPolicyInfoListView;
    @FXML
    private Button generateInfoListButton;
    @FXML
    private Button generatePHPInfobutton;

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

        //populateAreaComboBox();
    }

    public void toPolicyInfoRetrieval(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("retrievePolicyInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toRetrievalListPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("holderPolicyInfo.fxml"));
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

            if (!(omang.matches("[0-9]+") && omang.length() ==9)) {
                phPageOutputLabel.setText("Omang should only contain digits and be of length 9");
                return;
            }

            if (!(fName.matches("[a-zA-Z]+") && fName.length() <30)) {
                phPageOutputLabel.setText("First name should only contain letters and be of max length 30");
                return;
            }

            if (!(lName.matches("[a-zA-Z]+") && lName.length() <30)) {
                phPageOutputLabel.setText("Last name should only contain letters and be of max length 30");
                return;
            }

            if (!(email.matches("(.+)@(.+)") && email.length()<40)) {
                phPageOutputLabel.setText("Enter a valid email");
                return;
            }

            if (!(address.length()<40)) {
                phPageOutputLabel.setText("Address is too long");
                return;
            }

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String insertData = "INSERT INTO `T_PolicyHolder` (`Omang`, `FName`, `LName`, `Email`, `BranchID`, `Address`) " + "VALUES ('"+omang+"', '"+fName+"', '"+lName+"', '"+email+"', '12345', '"+address+"');";

            try{
                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate(insertData);
                phPageOutputLabel.setText("Record inserted successfully!");
            }catch (Exception e){
                phPageOutputLabel.setText("Error inserting record!");
                e.printStackTrace();
            }
        }else{
            phPageOutputLabel.setText("Please fill out all fields.");
        }
    }

    public void populateAreaComboBox(){
        DBConnect connection = new DBConnect();
        Connection connectionDB = connection.getConnection();

        String queryArea = "SELECT AName FROM `T_Area`; ";

        try{
            Statement stmt = connectionDB.createStatement();
            ResultSet result = stmt.executeQuery(queryArea);

            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()){
                String aName = result.getString("AName");
                listData.add(aName);
            }
            areaNameComboBox.setItems(listData);

        }catch (Exception e){
            e.printStackTrace();
        }

        String queryPolicy = "SELECT PolicyNo FROM `T_Policy`; ";

        try{
            Statement stmt = connectionDB.createStatement();
            ResultSet result = stmt.executeQuery(queryPolicy);

            ObservableList listData2 = FXCollections.observableArrayList();
            while(result.next()){
                String policyNum = result.getString("PolicyNo");
                listData2.add(policyNum);
            }
            policyNoComboBox.setItems(listData2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void submitProductInfo(ActionEvent a){

        String policyNumber = policyNoComboBox.getSelectionModel().getSelectedItem();
        String areaName = areaNameComboBox.getSelectionModel().getSelectedItem();

        if(productIDTextField.getText().isBlank()==false && productNameTextField.getText().isBlank()==false && productTypeTextField.getText().isBlank()==false && policyNumber.isBlank()==false && areaName.isBlank()==false){

            String productID = this.productIDTextField.getText().toString();
            String productName = this.productNameTextField.getText().toString();
            String productType = this.productTypeTextField.getText().toString();

            if (!(productID.matches("[0-9]+") && productID.length() ==5)) {
                productPageOutputLabel.setText("Product ID should only contain digits and be of length 5");
                return;
            }

            if (productName.length() >30) {
                productPageOutputLabel.setText("Product name should be of max length 30");
                return;
            }

            if (productType.length() >20) {
                productPageOutputLabel.setText("Type name should be of max length 20");
                return;
            }

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String insertData = "INSERT INTO `T_Product` (`ProductID`, `ProductName`, `Type`, `PolicyNo`, `AName`) " + "VALUES ('"+productID+"', '"+productName+"', '"+productType+"', '"+policyNumber+"', '"+areaName+"');";

            try{
                Statement stmt = connectionDB.createStatement();
                stmt.executeUpdate(insertData);

                productPageOutputLabel.setText("Record inserted successfully.");
            }catch (Exception e){
                productPageOutputLabel.setText("Error inserting record.");
                e.printStackTrace();
            }
        }else{
            productPageOutputLabel.setText("Fill in all fields.");
        }
    }

    //for retrieving policy information based on claim no or omang number
    public void retrievePolicyInfo(ActionEvent event){

        if(omangNoRetrieval.getText().isBlank()==false){

            String omangRetrieval = this.omangNoRetrieval.getText().toString();

            if (!(omangRetrieval.matches("[0-9]+") && omangRetrieval.length() ==9)) {
                retPolicyOutputLabel.setText("Omang should only contain digits and be of length 9");
                return;
            }

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String queryPolicy = "SELECT PolicyNo, PolicyName FROM `T_Policy` WHERE Omang = '"+omangRetrieval+"'; ";

            try {
                Statement stmt = connectionDB.createStatement();
                ResultSet result = stmt.executeQuery(queryPolicy);

                while(result.next()){
                    String pNo = result.getString("PolicyNo");
                    String pName = result.getString("PolicyName");
                    policyRetListView.getItems().add(pNo+"      "+pName);
                }


            }catch (Exception e){
                retPolicyOutputLabel.setText("Error retrieving information");
                e.printStackTrace();
            }

        }
        if(claimNoRetrieval.getText().isBlank()==false){

            String claimNoRet = this.claimNoRetrieval.getText().toString();

            if (!(claimNoRet.matches("[0-9]+") && claimNoRet.length() ==4)) {
                retPolicyOutputLabel.setText("Claim no should only contain digits and be of length 4");
                return;
            }

            DBConnect connection = new DBConnect();
            Connection connectionDB = connection.getConnection();

            String query = "SELECT T_Policy.PolicyNo, T_Policy.PolicyName FROM T_Policy, T_Claim WHERE T_Claim.ClaimNo='"+claimNoRet+"' AND T_Claim.PolicyNo = T_Policy.PolicyNo; ";

            try {
                Statement stmt = connectionDB.createStatement();
                ResultSet result = stmt.executeQuery(query);

                while(result.next()){
                    String pNo = result.getString("PolicyNo");
                    String pName = result.getString("PolicyName");
                    policyRetListView.getItems().add(pNo+"      "+pName);
                }


            }catch (Exception e){
                retPolicyOutputLabel.setText("Error retrieving information");
                e.printStackTrace();
            }
        }
    }

    //This function is used to retrieve a list of information that relates policies and policy holders
    public void retrieveListInfo(ActionEvent event){

        DBConnect connection = new DBConnect();
        Connection connectionDB = connection.getConnection();

        String queryList = "SELECT T_Policy.PolicyName, T_Product.ProductName, T_PolicyHolder.FName, T_PolicyHolder.LName, T_Broker.BName FROM T_Policy, T_PolicyHolder, T_Product, T_Broker WHERE T_PolicyHolder.Omang = T_Policy.Omang OR T_Policy.PolicyNo = T_Product.PolicyNo OR T_Policy.PolicyNo = T_Broker.PolicyNo";

        try {
            Statement stmt = connectionDB.createStatement();
            ResultSet result = stmt.executeQuery(queryList);

            while(result.next()){
                String pName = result.getString("PolicyName");
                String productName = result.getString("ProductName");
                String fName = result.getString("FName");
                String lName = result.getString("LName");
                String bName = result.getString("BName");
                pHolderPolicyInfoListView.getItems().add(pName+"        "+productName+"     "+fName+"       "+lName+"       "+bName);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}