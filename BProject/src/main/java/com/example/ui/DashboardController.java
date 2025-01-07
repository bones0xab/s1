package com.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

public class DashboardController {
    @FXML
    private Label lblWelcome;

    @FXML
    private TableView<?> tblInventory;

    @FXML
    private Button btnManageProducts;

    @FXML
    private Button btnViewTransactions;

    @FXML
    public void initialize() {
        lblWelcome.setText("Welcome to Inventory Management Dashboard");
    }

    @FXML
    private void handleManageProducts() {
        System.out.println("Navigating to Product Management...");
    }

    @FXML
    private void handleViewTransactions() {
        System.out.println("Navigating to Transaction History...");
    }
}