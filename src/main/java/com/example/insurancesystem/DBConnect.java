package com.example.insurancesystem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
public class DBConnect {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName="db_mos05233";
        String databaseUser="mos05233";
        String databasePassword="mos05233";
        String url = "jdbc:mysql://10.0.19.74/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;

    }
}
