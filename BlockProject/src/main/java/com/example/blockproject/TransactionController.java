package com.example.blockproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.time.LocalDateTime;

public class TransactionController {
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, Integer> idColumn;
    @FXML private TableColumn<Transaction, String> productColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Integer> quantityColumn;
    @FXML private TableColumn<Transaction, LocalDateTime> dateColumn;

    @FXML private ComboBox<String> productComboBox;
    @FXML private ComboBox<String> transactionTypeComboBox;
    @FXML private TextField quantityField;
    @FXML private TextArea notesArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Im-the-1&^_^";

    @FXML
    public void initialize() {
        // Initialize ComboBox items
        transactionTypeComboBox.setItems(FXCollections.observableArrayList("IN", "OUT"));
        loadProducts();
        loadTransactions();
        setupTableColumns();
    }

    private void loadProducts() {
        String sql = "SELECT name FROM products";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            ObservableList<String> products = FXCollections.observableArrayList();
            while (rs.next()) {
                products.add(rs.getString("name"));
            }
            productComboBox.setItems(products);

        } catch (SQLException e) {
            showAlert("Error loading products: " + e.getMessage());
        }
    }

    @FXML
    protected void handleAddTransaction() {
        if (!validateInputs()) {
            return;
        }

        String productName = productComboBox.getValue();
        String transactionType = transactionTypeComboBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());
        String notes = notesArea.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);
            try {
                // Get product ID and current quantity
                int productId;
                int currentQuantity;
                String getProductSql = "SELECT id, quantity FROM products WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(getProductSql)) {
                    pstmt.setString(1, productName);
                    ResultSet rs = pstmt.executeQuery();
                    if (!rs.next()) {
                        throw new SQLException("Product not found");
                    }
                    productId = rs.getInt("id");
                    currentQuantity = rs.getInt("quantity");
                }

                // Validate stock levels for OUT transactions
                if (transactionType.equals("OUT") && quantity > currentQuantity) {
                    throw new SQLException("Insufficient stock");
                }

                // Insert transaction
                String insertTransactionSql = "INSERT INTO transactions (product_id, transaction_type, quantity, notes) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertTransactionSql)) {
                    pstmt.setInt(1, productId);
                    pstmt.setString(2, transactionType);
                    pstmt.setInt(3, quantity);
                    pstmt.setString(4, notes);
                    pstmt.executeUpdate();
                }

                // Update product quantity
                int newQuantity = transactionType.equals("IN") ?
                        currentQuantity + quantity :
                        currentQuantity - quantity;

                String updateProductSql = "UPDATE products SET quantity = ? WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateProductSql)) {
                    pstmt.setInt(1, newQuantity);
                    pstmt.setInt(2, productId);
                    pstmt.executeUpdate();
                }

                conn.commit();
                clearInputs();
                loadTransactions();
                showAlert("Transaction added successfully!");

            } catch (SQLException e) {
                conn.rollback();
                showAlert("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            showAlert("Database error: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        String sql = """
            SELECT t.id, p.name as product_name, t.transaction_type, 
                   t.quantity, t.transaction_date, t.notes
            FROM transactions t
            JOIN products p ON t.product_id = p.id
            ORDER BY t.transaction_date DESC
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            ObservableList<Transaction> transactions = FXCollections.observableArrayList();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getString("transaction_type"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("transaction_date").toLocalDateTime(),
                        rs.getString("notes")
                ));
            }

            transactionTable.setItems(transactions);

        } catch (SQLException e) {
            showAlert("Error loading transactions: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
    }

    private boolean validateInputs() {
        if (productComboBox.getValue() == null ||
                transactionTypeComboBox.getValue() == null ||
                quantityField.getText().isEmpty()) {
            showAlert("Please fill in all required fields");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                showAlert("Quantity must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid quantity format");
            return false;
        }

        return true;
    }

    private void clearInputs() {
        productComboBox.setValue(null);
        transactionTypeComboBox.setValue(null);
        quantityField.clear();
        notesArea.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToDashboard() {
        SceneManager.switchScene("Dashboard.fxml");
    }
}