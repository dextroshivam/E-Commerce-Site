module com.example.ecom {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecom to javafx.fxml;
    exports com.example.ecom;
}