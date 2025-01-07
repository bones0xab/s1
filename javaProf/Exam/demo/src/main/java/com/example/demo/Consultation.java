package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Consultation {
    private int idConsultation;
    private Date dateConsultation;
    private Patient patient;
    private Medecin medecin;

    public Consultation(int idConsultation, Date dateConsultation,
                        Patient patient, Medecin medecin) {
        this.idConsultation = idConsultation;
        this.dateConsultation = dateConsultation;
        this.patient = patient;
        this.medecin = medecin;

        // Add this consultation to both patient and doctor's lists
        patient.getConsultations().add(this);
        medecin.getConsultations().add(this);
    }

    // Getters and Setters
    public int getIdConsultation() { return idConsultation; }
    public Date getDateConsultation() { return dateConsultation; }
    public Patient getPatient() { return patient; }
    public Medecin getMedecin() { return medecin; }
}