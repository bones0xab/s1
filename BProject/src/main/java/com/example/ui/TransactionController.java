package com.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}