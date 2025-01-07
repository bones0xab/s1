package com.example.demo;

import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    private int idPatient;
    private String nom;
    private String prenom;
    private String cin;
    private String telephone;
    private String email;
    private Date dateNaissance;
    private List<Consultation> consultations;

    public Patient(int idPatient, String nom, String prenom, String cin,
                   String telephone, String email, Date dateNaissance) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.telephone = telephone;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.consultations = new ArrayList<>();
    }

    public Patient(int idPatient) {
        this.idPatient = idPatient;
        this.consultations = new ArrayList<>();
    }

    public Patient(String name, String cin) {
    }

    // Getters and Setters
    public int getIdPatient() { return idPatient; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getCin() { return cin; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public Date getDateNaissance() { return dateNaissance; }
    public List<Consultation> getConsultations() { return consultations; }

    public int getId() {
        return idPatient;
    }

    
}