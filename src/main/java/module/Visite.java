package module;

import java.time.LocalDate;

public class Visite {
    private int id;                  // Identifiant unique de la visite
    private int patientId;           // Identifiant du patient lié à la visite
    private String patientNom;       // Nom du patient (utile pour affichage dans TableView)
    private LocalDate dateVisite;    // Date de la visite
    private String etatRendu;        // État de la visite : "Vu", "Absent", "Annulé"

    // Constructeur principal avec tous les champs (id inclus)
    public Visite(int id, int patientId, String patientNom, LocalDate dateVisite, String etatRendu) {
        this.id = id;
        this.patientId = patientId;
        this.patientNom = patientNom;
        this.dateVisite = dateVisite;
        this.etatRendu = etatRendu;
    }

    // Constructeur sans id et sans patientNom (ex: pour création avant insertion en BDD)
    public Visite(int patientId, LocalDate dateVisite, String etatRendu) {
        this(0, patientId, null, dateVisite, etatRendu);
    }

    // --- Getters et setters pour chaque propriété ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientNom() {
        return patientNom;
    }

    public void setPatientNom(String patientNom) {
        this.patientNom = patientNom;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getEtatRendu() {
        return etatRendu;
    }

    public void setEtatRendu(String etatRendu) {
        this.etatRendu = etatRendu;
    }
}
