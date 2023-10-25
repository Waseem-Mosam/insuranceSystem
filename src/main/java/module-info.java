module com.example.insurancesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.insurancesystem to javafx.fxml;
    exports com.example.insurancesystem;
}