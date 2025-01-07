package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Date;
import java.util.List;

public class CabinetController {

    // Patient form fields
    @FXML private TextField nameField, prenomField, cinField, telephoneField, emailField;
    @FXML private DatePicker birthDatePicker;
    @FXML private Button addPatientButton, deletePatientButton;
    @FXML private TableView<Patient> patientTable;

    // Medecin form fields
    @FXML private TextField medecinNameField, medecinSpecialtyField;
    @FXML private Button addMedecinButton, deleteMedecinButton;
    @FXML private TableView<Medecin> medecinTable;

    // Consultation form fields
    @FXML private ComboBox<Patient> patientComboBox;
    @FXML private ComboBox<Medecin> medecinComboBox;
    @FXML private DatePicker consultationDatePicker;
    @FXML private Button addConsultationButton, deleteConsultationButton;
    @FXML private TableView<Consultation> consultationTable;

    private ICabImpl icabImpl;

    public CabinetController() {
        this.icabImpl = new ICabImpl();  // Backend implementation for handling data
    }

    // Initialize method to set up ComboBoxes and TableView data
    @FXML
    public void initialize() {
        loadPatientData();
        loadMedecinData();
        loadConsultationData();

        // Add listeners for the tables (optional)
        patientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onPatientSelected(newValue));
        medecinTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onMedecinSelected(newValue));
    }

    // 1. Handle Patient Operations

    @FXML
    private void ajouterPatient() {
        // Retrieve the user inputs
        String nom = nameField.getText();
        String prenom = prenomField.getText();
        String cin = cinField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        Date dateNaissance = java.sql.Date.valueOf(birthDatePicker.getValue());

        // Validate inputs
        if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || telephone.isEmpty() || email.isEmpty() || dateNaissance == null) {
            showAlert("Error", "All fields must be filled out!");
            return;
        }

        // Create a new Patient object and add it to the backend
        Patient newPatient = new Patient(0, nom, prenom, cin, telephone, email, dateNaissance);
        icabImpl.ajouterPatient(newPatient);

        // Update the UI with the new list of patients
        loadPatientData();
        showAlert("Success", "Patient added successfully!");
    }

    @FXML
    private void supprimerPatient() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            icabImpl.supprimerPatient(selectedPatient.getIdPatient());
            loadPatientData();
            showAlert("Success", "Patient deleted successfully!");
        } else {
            showAlert("Error", "No patient selected!");
        }
    }

    // 2. Handle Medecin Operations

    @FXML
    private void ajouterMedecin() {
        // Retrieve the user inputs
        String name = medecinNameField.getText();
        String specialty = medecinSpecialtyField.getText();

        // Validate inputs
        if (name.isEmpty() || specialty.isEmpty()) {
            showAlert("Error", "All fields must be filled out!");
            return;
        }

        // Create a new Medecin object and add it to the backend
        Medecin newMedecin = new Medecin(0, name, specialty);
        icabImpl.ajouterMedecin(newMedecin);

        // Update the UI with the new list of medecins
        loadMedecinData();
        showAlert("Success", "Doctor added successfully!");
    }

    @FXML
    private void supprimerMedecin() {
        Medecin selectedMedecin = medecinTable.getSelectionModel().getSelectedItem();
        if (selectedMedecin != null) {
            icabImpl.supprimerMedecin(selectedMedecin.getIdMedecin());
            loadMedecinData();
            showAlert("Success", "Doctor deleted successfully!");
        } else {
            showAlert("Error", "No doctor selected!");
        }
    }

    // 3. Handle Consultation Operations

    /*
    @FXML
    private void ajouterConsultation() {
        Patient selectedPatient = patientComboBox.getValue();
        Medecin selectedMedecin = medecinComboBox.getValue();
        Date consultationDate = java.sql.Date.valueOf(consultationDatePicker.getValue());

        // Validate inputs
        if (selectedPatient == null || selectedMedecin == null || consultationDate == null) {
            showAlert("Error", "All fields must be filled out!");
            return;
        }

        // Create a new Consultation object and add it to the backend
        Consultation newConsultation = new Consultation(0, selectedPatient.getDateNaissance(), selectedMedecin.getIdMedecin(), consultationDate);
        icabImpl.ajouterConsultation(newConsultation);

        // Update the UI with the new list of consultations
        loadConsultationData();
        showAlert("Success", "Consultation added successfully!");
    }
*/
    @FXML
    private void supprimerConsultation() {
        Consultation selectedConsultation = consultationTable.getSelectionModel().getSelectedItem();
        if (selectedConsultation != null) {
            icabImpl.supprimerConsultation(selectedConsultation.getIdConsultation());
            loadConsultationData();
            showAlert("Success", "Consultation deleted successfully!");
        } else {
            showAlert("Error", "No consultation selected!");
        }
    }

    // Helper methods to load data into the tables

    private void loadPatientData() {
        List<Patient> patients = icabImpl.afficherListePatients();
        patientTable.getItems().setAll(patients);
    }

    private void loadMedecinData() {
        List<Medecin> medecins = icabImpl.afficherListeMedecins();
        medecinTable.getItems().setAll(medecins);
    }

    private void loadConsultationData() {
        List<Consultation> consultations = icabImpl.afficherListeConsultations();
        consultationTable.getItems().setAll(consultations);
    }

    // Show alert dialog with a given message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // On selecting a patient in the table, update the ComboBox
    private void onPatientSelected(Patient patient) {
        if (patient != null) {
            patientComboBox.setValue(patient);
        }
    }

    // On selecting a doctor in the table, update the ComboBox
    private void onMedecinSelected(Medecin medecin) {
        if (medecin != null) {
            medecinComboBox.setValue(medecin);
        }
    }
}
