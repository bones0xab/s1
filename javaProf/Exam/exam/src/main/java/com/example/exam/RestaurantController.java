package com.example.exam;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RestaurantController {

    @FXML private TextField clientNomField;
    @FXML private TextField clientPrenomField;
    @FXML private TextField clientEmailField;
    @FXML private TextField clientTelephoneField;
    @FXML private Button addClientButton;
    @FXML private Button deleteClientButton;

    @FXML private ComboBox<Client> commandeClientComboBox;
    @FXML private TextField commandeStatutField;
    @FXML private TextField commandeTotalField;
    @FXML private Button addCommandeButton;
    @FXML private Button deleteCommandeButton;

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Integer> clientIdColumn;
    @FXML private TableColumn<Client, String> clientNomColumn;
    @FXML private TableColumn<Client, String> clientPrenomColumn;
    @FXML private TableColumn<Client, String> clientEmailColumn;
    @FXML private TableColumn<Client, String> clientTelephoneColumn;

    @FXML private TableView<Commande> commandeTable;
    @FXML private TableColumn<Commande, Integer> commandeIdColumn;
    @FXML private TableColumn<Commande, String> commandeClientColumn;
    @FXML private TableColumn<Commande, String> commandeStatutColumn;
    @FXML private TableColumn<Commande, Double> commandeTotalColumn;
    @FXML private TableColumn<Commande, LocalDateTime> commandeDateColumn;


    @FXML private TextField platNomField;
    @FXML private TextField platDescriptionField;
    @FXML private TextField platPrixBaseField;
    @FXML private Button addPlatButton;
    @FXML private Button deletePlatButton;
    @FXML private TableView<PlatPrincipal> platTable;
    @FXML private TableColumn<PlatPrincipal, Integer> platIdColumn;
    @FXML private TableColumn<PlatPrincipal, String> platNomColumn;
    @FXML private TableColumn<PlatPrincipal, String> platDescriptionColumn;
    @FXML private TableColumn<PlatPrincipal, BigDecimal> platPrixBaseColumn;
    @FXML private TableColumn<PlatPrincipal, Boolean> platDisponibleColumn;

    @FXML private TextField ingredientNomField;
    @FXML private TextField ingredientUniteField;
    @FXML private TextField ingredientPrixUnitaireField;
    @FXML private Button addIngredientButton;
    @FXML private Button deleteIngredientButton;
    @FXML private TableView<Ingredient> ingredientTable;
    @FXML private TableColumn<Ingredient, Integer> ingredientIdColumn;
    @FXML private TableColumn<Ingredient, String> ingredientNomColumn;
    @FXML private TableColumn<Ingredient, String> ingredientUniteColumn;
    @FXML private TableColumn<Ingredient, BigDecimal> ingredientPrixUnitaireColumn;
    @FXML private TableColumn<Ingredient, Boolean> ingredientDisponibleColumn;

    @FXML private VBox platSection;
    @FXML private VBox ingredientSection;

    private PlatPrincipalDAO platDAO = new PlatPrincipalDAO();
    private IngredientDAO ingredientDAO = new IngredientDAO();
    private ObservableList<PlatPrincipal> plats = FXCollections.observableArrayList();
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    @FXML private VBox clientSection;
    @FXML private VBox commandeSection;

    private ClientDAO clientDAO = new ClientDAO();
    private CommandeDAO commandeDAO = new CommandeDAO();
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<Commande> commandes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        loadClients();
        loadCommandes();
        loadPlats();
        loadIngredients();
        // Initialize Plat Table Columns
        platIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        platNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        platDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        platPrixBaseColumn.setCellValueFactory(new PropertyValueFactory<>("prixBase"));
        platDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // Initialize Ingredient Table Columns
        ingredientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ingredientNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ingredientUniteColumn.setCellValueFactory(new PropertyValueFactory<>("unite"));
        ingredientPrixUnitaireColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        ingredientDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // Load plats and ingredients from the database


        // Bind the Tables to the ObservableLists
        platTable.setItems(plats);
        ingredientTable.setItems(ingredients);

        // Set up button actions
        addPlatButton.setOnAction(e -> addPlat());
        deletePlatButton.setOnAction(e -> deletePlat());
        addIngredientButton.setOnAction(e -> addIngredient());
        deleteIngredientButton.setOnAction(e -> deleteIngredient());
        // Initialize Client Table Columns
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        clientPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientTelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Initialize Commande Table Columns
        commandeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        commandeClientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        commandeStatutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        commandeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        commandeDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));

        // Load clients and commandes from the database


        // Bind the Tables to the ObservableLists
        clientTable.setItems(clients);
        commandeTable.setItems(commandes);

        // Populate the ComboBox with clients
        commandeClientComboBox.setItems(clients);

        // Set up button actions
        addClientButton.setOnAction(e -> addClient());
        deleteClientButton.setOnAction(e -> deleteClient());
        addCommandeButton.setOnAction(e -> addCommande());
        deleteCommandeButton.setOnAction(e -> deleteCommande());
    }

    @FXML
    private void showPlatSection() {
        platSection.setVisible(true);
        ingredientSection.setVisible(false);
        clientSection.setVisible(false);
        commandeSection.setVisible(false);
    }

    @FXML
    private void showIngredientSection() {
        platSection.setVisible(false);
        ingredientSection.setVisible(true);
        clientSection.setVisible(false);
        commandeSection.setVisible(false);
    }

    private void loadPlats() {
        try {
            plats.clear();
            plats.addAll(platDAO.findAll());
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load plats: " + e.getMessage());
        }
    }

    private void loadIngredients() {
        try {
            ingredients.clear();
            ingredients.addAll(ingredientDAO.findAll());
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load ingredients: " + e.getMessage());
        }
    }

    @FXML
    private void addPlat() {
        String nom = platNomField.getText();
        String description = platDescriptionField.getText();
        BigDecimal prixBase = new BigDecimal(platPrixBaseField.getText());

        if (nom.isEmpty() || description.isEmpty() || prixBase == null) {
            showAlert("Input Error", "All fields are required.");
            return;
        }

        PlatPrincipal plat = new PlatPrincipal(nom, description, prixBase);
        try {
            platDAO.save(plat);
            plats.add(plat);
            clearPlatFields();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save plat: " + e.getMessage());
        }
    }

    @FXML
    private void deletePlat() {
        PlatPrincipal selectedPlat = platTable.getSelectionModel().getSelectedItem();
        if (selectedPlat == null) {
            showAlert("Selection Error", "No plat selected.");
            return;
        }

        try {
            platDAO.delete(selectedPlat.getId());
            plats.remove(selectedPlat);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to delete plat: " + e.getMessage());
        }
    }

    @FXML
    private void addIngredient() {
        String nom = ingredientNomField.getText();
        String unite = ingredientUniteField.getText();
        BigDecimal prixUnitaire = new BigDecimal(ingredientPrixUnitaireField.getText());

        if (nom.isEmpty() || unite.isEmpty() || prixUnitaire == null) {
            showAlert("Input Error", "All fields are required.");
            return;
        }

        Ingredient ingredient = new Ingredient(nom, unite, prixUnitaire);
        try {
            ingredientDAO.save(ingredient);
            ingredients.add(ingredient);
            clearIngredientFields();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save ingredient: " + e.getMessage());
        }
    }

    @FXML
    private void deleteIngredient() {
        Ingredient selectedIngredient = ingredientTable.getSelectionModel().getSelectedItem();
        if (selectedIngredient == null) {
            showAlert("Selection Error", "No ingredient selected.");
            return;
        }

        try {
            ingredientDAO.delete(selectedIngredient.getId());
            ingredients.remove(selectedIngredient);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to delete ingredient: " + e.getMessage());
        }
    }

    private void clearPlatFields() {
        platNomField.clear();
        platDescriptionField.clear();
        platPrixBaseField.clear();
    }

    private void clearIngredientFields() {
        ingredientNomField.clear();
        ingredientUniteField.clear();
        ingredientPrixUnitaireField.clear();
    }

    @FXML
    private void showClientSection() {
        clientSection.setVisible(true);
        commandeSection.setVisible(false);
        platSection.setVisible(false);
        ingredientSection.setVisible(false);
    }

    @FXML
    private void showCommandeSection() {
        clientSection.setVisible(false);
        commandeSection.setVisible(true);
        platSection.setVisible(false);
        ingredientSection.setVisible(false);
    }

    private void loadClients() {
        try {
            clients.clear();
            clients.addAll(clientDAO.findAll());
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load clients: " + e.getMessage());
        }
    }

    private void loadCommandes() {
        try {
            commandes.clear();
            for (Commande commande : commandeDAO.findAll()) {
                commandes.add(commande);
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load commandes: " + e.getMessage());
        }
    }

    @FXML
    private void addClient() {
        String nom = clientNomField.getText();
        String prenom = clientPrenomField.getText();
        String email = clientEmailField.getText();
        String telephone = clientTelephoneField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
            showAlert("Input Error", "All fields are required.");
            return;
        }

        Client client = new Client(nom, prenom, email, telephone);
        try {
            clientDAO.save(client);
            clients.add(client);
            clearClientFields();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save client: " + e.getMessage());
        }
    }

    @FXML
    private void deleteClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("Selection Error", "No client selected.");
            return;
        }

        try {
            clientDAO.delete(selectedClient.getId());
            clients.remove(selectedClient);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to delete client: " + e.getMessage());
        }
    }

    @FXML
    private void addCommande() {
        Client selectedClient = commandeClientComboBox.getSelectionModel().getSelectedItem();
        String statut = commandeStatutField.getText();
        double total = Double.parseDouble(commandeTotalField.getText());

        if (selectedClient == null || statut.isEmpty() || total < 0) {
            showAlert("Input Error", "Invalid input for commande.");
            return;
        }

        Commande commande = new Commande(selectedClient);
        commande.setStatut(statut);
        commande.setTotal(total);
        commande.setCreatedAt(LocalDateTime.now());

        try {
            commandeDAO.save(commande);
            commandes.add(commande);
            clearCommandeFields();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save commande: " + e.getMessage());
        }
    }





    @FXML
    private void deleteCommande() {
        Commande selectedCommande = commandeTable.getSelectionModel().getSelectedItem();
        if (selectedCommande == null) {
            showAlert("Selection Error", "No commande selected.");
            return;
        }

        try {
            commandeDAO.delete(selectedCommande.getId());
            commandes.remove(selectedCommande);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to delete commande: " + e.getMessage());
        }
    }

    private void clearClientFields() {
        clientNomField.clear();
        clientPrenomField.clear();
        clientEmailField.clear();
        clientTelephoneField.clear();
    }

    private void clearCommandeFields() {
        commandeClientComboBox.getSelectionModel().clearSelection();
        commandeStatutField.clear();
        commandeTotalField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}