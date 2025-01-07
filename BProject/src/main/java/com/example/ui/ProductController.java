package com.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProductController {
    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Spinner<Integer> spnQuantity;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private void handleSaveProduct() {
        System.out.println("Saving product...");
    }

    @FXML
    private void handleDeleteProduct() {
        System.out.println("Deleting product...");
    }

    @FXML
    private void handleClearFields() {
        txtProductId.clear();
        txtProductName.clear();
        txtDescription.clear();
        spnQuantity.getValueFactory().setValue(0);
        txtPrice.clear();
    }
}