package module;

import java.time.LocalDate;

public class Visite {
    private int id;
    private int patientId;
    private String patientNom;    // pour affichage dans TableView
    private LocalDate dateVisite;
    private String etatRendu;     // Vu, Absent, Annulé

    public Visite(int id, int patientId, String patientNom, LocalDate dateVisite, String etatRendu) {
        this.id = id;
        this.patientId = patientId;
        this.patientNom = patientNom;
        this.dateVisite = dateVisite;
        this.etatRendu = etatRendu;
    }

    public Visite(int patientId, LocalDate dateVisite, String etatRendu) {
        this(0, patientId, null, dateVisite, etatRendu);
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }

    public LocalDate getDateVisite() { return dateVisite; }
    public void setDateVisite(LocalDate dateVisite) { this.dateVisite = dateVisite; }

    public String getEtatRendu() { return etatRendu; }
    public void setEtatRendu(String etatRendu) { this.etatRendu = etatRendu; }
}
