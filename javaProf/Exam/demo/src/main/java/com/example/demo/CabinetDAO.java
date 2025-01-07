package com.example.demo;

import java.util.ArrayList;
import java.util.List;

// Unified DAO for managing all operations
public class CabinetDAO {
    private List<Patient> patients = new ArrayList<>();
    private List<Medecin> medecins = new ArrayList<>();
    private List<Consultation> consultations = new ArrayList<>();

    // Patient operations
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public List<Patient> searchPatients(String keyword) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getName().contains(keyword) || patient.getCin().contains(keyword)) {
                result.add(patient);
            }
        }
        return result;
    }

    public void deletePatient(int idPatient) {
        patients.removeIf(patient -> patient.getId() == idPatient);
    }

    public List<Consultation> getConsultationsByPatient(int idPatient) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getPatient().getId() == idPatient) {
                result.add(consultation);
            }
        }
        return result;
    }

    // Medecin operations
    public void addMedecin(Medecin medecin) {
        medecins.add(medecin);
    }

    public List<Medecin> getAllMedecins() {
        return new ArrayList<>(medecins);
    }

    public void deleteMedecin(int idMedecin) {
        medecins.removeIf(medecin -> medecin.getId() == idMedecin);
    }

    public List<Consultation> getConsultationsByMedecin(int idMedecin) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (consultation.getMedecin().getId() == idMedecin) {
                result.add(consultation);
            }
        }
        return result;
    }

    // Consultation operations
    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public List<Consultation> getAllConsultations() {
        return new ArrayList<>(consultations);
    }

    public void deleteConsultation(int idConsultation) {
        consultations.removeIf(consultation -> consultation.getId() == idConsultation);
    }
}
