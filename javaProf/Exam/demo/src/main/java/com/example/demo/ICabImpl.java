package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ICabImpl implements ICabinetMedical {

    // Patient Management Methods (already implemented as above)

    @Override
    public void ajouterPatient(Patient patient) {
        String sql = "INSERT INTO patients (id_patient, nom, prenom, cin, telephone, email, date_naissance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patient.getIdPatient());
            stmt.setString(2, patient.getNom());
            stmt.setString(3, patient.getPrenom());
            stmt.setString(4, patient.getCin());
            stmt.setString(5, patient.getTelephone());
            stmt.setString(6, patient.getEmail());
            stmt.setDate(7, new java.sql.Date(patient.getDateNaissance().getTime()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> afficherListePatients() {
        List<Patient> result = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = SingletonConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_naissance")
                );
                result.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void supprimerPatient(int idPatient) {
        String sql = "DELETE FROM patients WHERE id_patient = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPatient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> afficherConsultationsPatient(int idPatient) {
        return List.of();
    }

    @Override
    public List<Patient> rechercherPatients(String motCle) {
        List<Patient> result = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE nom LIKE ? OR prenom LIKE ? OR cin LIKE ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchTerm = "%" + motCle + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_naissance")
                );
                result.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Medecin Management Methods

    @Override
    public void ajouterMedecin(Medecin medecin) {
        String sql = "INSERT INTO medecins (nom, specialite) VALUES (?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medecin.getNom());
            stmt.setString(2, medecin.getTel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medecin> afficherListeMedecins() {
        List<Medecin> result = new ArrayList<>();
        String sql = "SELECT * FROM medecins";
        try (Connection conn = SingletonConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medecin medecin = new Medecin(
                        rs.getInt("idMedecin"),
                        rs.getString("nom"),
                        rs.getString("specialite")
                );
                result.add(medecin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void supprimerMedecin(int idMedecin) {
        String sql = "DELETE FROM medecins WHERE idMedecin = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedecin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> afficherConsultationsMedecin(int idMedecin) {
        return List.of();
    }

    // Consultation Management Methods

    @Override
    public void ajouterConsultation(Consultation consultation) {
        String sql = "INSERT INTO consultations (idPatient, idMedecin, dateConsultation, motif) VALUES (?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consultation.getIdPatient());
            stmt.setInt(2, consultation.getIdMedecin());
            stmt.setDate(3, new java.sql.Date(consultation.getDateConsultation().getTime()));
            stmt.setString(4, consultation.getDateConsultation().toGMTString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> afficherListeConsultations() {
        List<Consultation> result = new ArrayList<>();
        String sql = "SELECT * FROM consultations";
        try (Connection conn = SingletonConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consultation consultation = new Consultation(
                        rs.getInt("idConsultation"),
                        rs.getDate("dateConsultation"),
                        rs.getInt("idPatient"),
                        rs.getInt("idMedecin")
                );
                result.add(consultation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void supprimerConsultation(int idConsultation) {
        String sql = "DELETE FROM consultations WHERE idConsultation = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConsultation);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
