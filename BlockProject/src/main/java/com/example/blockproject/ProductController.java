package com.example.blockproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class ProductController {
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> descriptionColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, BigDecimal> priceColumn;

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadProducts();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(products);
    }

    public void loadProducts() {
        products.clear();
        String query = "SELECT * FROM products";

        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleAddProduct() {
        try {
            String query = "INSERT INTO products (name, description, quantity, price) VALUES (?, ?, ?, ?)";

            try (Connection conn = SingletonConnexionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, nameField.getText());
                stmt.setString(2, descriptionField.getText());
                stmt.setInt(3, Integer.parseInt(quantityField.getText()));
                stmt.setBigDecimal(4, new BigDecimal(priceField.getText()));

                stmt.executeUpdate();
                clearFields();
                loadProducts();
                showAlert(AlertType.INFORMATION, "Success", "Product added successfully");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to add product: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid number format");
        }
    }

    @FXML
    private void handleUpdateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(AlertType.WARNING, "Warning", "Please select a product");
            return;
        }

        try {
            String query = "UPDATE products SET name=?, description=?, quantity=?, price=? WHERE id=?";

            try (Connection conn = SingletonConnexionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, nameField.getText());
                stmt.setString(2, descriptionField.getText());
                stmt.setInt(3, Integer.parseInt(quantityField.getText()));
                stmt.setBigDecimal(4, new BigDecimal(priceField.getText()));
                stmt.setInt(5, selectedProduct.getId());

                stmt.executeUpdate();
                clearFields();
                loadProducts();
                showAlert(AlertType.INFORMATION, "Success", "Product updated successfully");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to update product: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(AlertType.WARNING, "Warning", "Please select a product");
            return;
        }

        try {
            String query = "DELETE FROM products WHERE id=?";

            try (Connection conn = SingletonConnexionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, selectedProduct.getId());
                stmt.executeUpdate();

                loadProducts();
                clearFields();
                showAlert(AlertType.INFORMATION, "Success", "Product deleted successfully");
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to delete product: " + e.getMessage());
        }
    }

    @FXML
    private void handleProductSelection() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            nameField.setText(selectedProduct.getName());
            descriptionField.setText(selectedProduct.getDescription());
            quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
            priceField.setText(selectedProduct.getPrice().toString());
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}