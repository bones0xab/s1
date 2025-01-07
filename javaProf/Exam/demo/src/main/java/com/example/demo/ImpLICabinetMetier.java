package com.example.demo;


import java.util.ArrayList;
import java.util.List;

public class ImpLICabinetMetier {
    private List<Patient> patients;
    private List<Medecin> medecins;
    private List<Consultation> consultations;

    public ImpLICabinetMetier() {
        this.patients = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.consultations = new ArrayList<>();
    }

    // Patient Management
    public void ajouterPatient(Patient patient) {
        patients.add(patient);
    }

    public void supprimerPatient(Patient patient) {
        patients.remove(patient);
    }

    public List<Patient> rechercherPatients(String motCle) {
        List<Patient> resultats = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getNom().toLowerCase().contains(motCle.toLowerCase()) ||
                    patient.getPrenom().toLowerCase().contains(motCle.toLowerCase())) {
                resultats.add(patient);
            }
        }
        return resultats;
    }

    // Doctor Management
    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
    }

    public void supprimerMedecin(Medecin medecin) {
        medecins.remove(medecin);
    }

    // Consultation Management
    public void ajouterConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public void supprimerConsultation(Consultation consultation) {
        consultations.remove(consultation);
    }

    // Getters for lists
    public List<Patient> getPatients() { return patients; }
    public List<Medecin> getMedecins() { return medecins; }
    public List<Consultation> getConsultations() { return consultations; }
}