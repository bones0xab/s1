package com.example.demo;

import java.util.List;

public interface ICabinetMedical {
    // 1. Patient Management Methods
    List<Patient> afficherListePatients();
    List<Patient> rechercherPatients(String motCle);
    void ajouterPatient(Patient patient);
    void supprimerPatient(int idPatient);
    List<Consultation> afficherConsultationsPatient(int idPatient);

    // 2. Doctor Management Methods
    void ajouterMedecin(Medecin medecin);
    List<Medecin> afficherListeMedecins();
    void supprimerMedecin(int idMedecin);
    List<Consultation> afficherConsultationsMedecin(int idMedecin);

    // 3. Consultation Management Methods
    void ajouterConsultation(Consultation consultation);
    List<Consultation> afficherListeConsultations();
    void supprimerConsultation(int idConsultation);
}