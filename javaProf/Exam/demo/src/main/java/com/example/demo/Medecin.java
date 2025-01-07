package com.example.demo;

import java.util.ArrayList;
import java.util.List;


public class Medecin {
    private int idMedecin;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private List<Consultation> consultations;

    public Medecin(int idMedecin, String nom, String prenom, String email, String tel) {
        this.idMedecin = idMedecin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.consultations = new ArrayList<>();
    }
    public Medecin(int idMedecin) {
        this.idMedecin = idMedecin;
        this.consultations = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdMedecin() { return idMedecin; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTel() { return tel; }
    public List<Consultation> getConsultations() { return consultations; }
}