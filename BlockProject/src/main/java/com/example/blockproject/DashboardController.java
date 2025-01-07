package com.example.blockproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.Objects;

public class DashboardController {
    @FXML private Label lblWelcome;
    @FXML private TableView<Product> tblInventory;
    @FXML private TableColumn<Product, Integer> colProductId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Double> colPrice;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private Button btnManageProducts;
    @FXML private Button btnViewTransactions;

    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (lblWelcome != null) {
            lblWelcome.setText("Welcome to Inventory Management Dashboard");
        }

        if (checkRequiredComponents()) {
            loadProductsFromDatabase();
            setupTableColumns();
        } else {
            showError("Initialization Error", "Some UI components failed to load properly.");
        }
    }

    private boolean checkRequiredComponents() {
        return tblInventory != null &&
                colName != null &&
                colPrice != null &&
                colQuantity != null;
    }

    private void setupTableColumns() {
        try {
            System.out.println("colId: " + colProductId);
            System.out.println("colName: " + colName);
            System.out.println("colPrice: " + colPrice);
            System.out.println("colQuantity: " + colQuantity);
            System.out.println("tblInventory: " + tblInventory);

            colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            tblInventory.setItems(productList);
        } catch (NullPointerException e) {
            showError("Setup Error", "Error setting up table columns: " + e.getMessage());
        }
    }

    private void loadProductsFromDatabase() {
        if (tblInventory == null) {
            showError("Loading Error", "Table view not initialized");
            return;
        }

        String query = "SELECT * FROM products";
        try (Connection conn = SingletonConnexionDB.getConnection()) {
            if (conn == null) {
                showError("Database Error", "Could not connect to database");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                productList.clear();
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("quantity"),
                            rs.getBigDecimal("price")
                    );
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            showError("Database Error", "Error loading products: " + e.getMessage());
        }
    }

    @FXML
    private void handleManageProducts() {
        if (btnManageProducts != null) {
            System.out.println("Navigating to Product Management...");
            SceneManager.switchScene("ProductForm.fxml");
        }
    }

    @FXML
    private void handleViewTransactions() {
        if (btnViewTransactions != null) {
            System.out.println("Navigating to Transaction History...");
            SceneManager.switchScene("TransactionForm.fxml");
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void refreshTable() {
        if (checkRequiredComponents()) {
            loadProductsFromDatabase();
        }
    }
}