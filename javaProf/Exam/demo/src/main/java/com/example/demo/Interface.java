package com.example.demo;



import java.util.ArrayList;
import java.util.List;

public interface Interface {

    // Patient Management.
     void ajouterPatient(Patient patient);

     void supprimerPatient(Patient patient);

     List<Patient> rechercherPatients(String motCle);

    // Doctor Management
    void ajouterMedecin(Medecin medecin);

    void supprimerMedecin(Medecin medecin);

    // Consultation Management
    void ajouterConsultation(Consultation consultation);

    public void supprimerConsultation(Consultation consultation);

    // Getters for lists
    public List<Patient> getPatients();
    public List<Medecin> getMedecins();
    public List<Consultation> getConsultations();
}