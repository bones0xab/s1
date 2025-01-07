module com.example.blockproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.blockproject to javafx.fxml;
    exports com.example.blockproject;
}