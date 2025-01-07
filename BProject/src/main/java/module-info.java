module com.example.bproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
    opens Main to javafx.fxml;
}