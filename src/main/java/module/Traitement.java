/*package module;

import java.time.LocalDate;

public class Traitement {
    private int id;
    private int patientId;
    private int medecinId;
    private String medicament;
    private String dose;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Constructeur
    public Traitement(int id, int patientId, int medecinId, String medicament, String dose, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.medicament = medicament;
        this.dose = dose;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getMedecinId() {
        return medecinId;
    }

    public String getMedicament() {
        return medicament;
    }

    public String getDose() {
        return dose;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setMedecinId(int medecinId) {
        this.medecinId = medecinId;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
 */
package module;

public class Traitement {
    private int id;
    private int patientId;
    private int medecinId;
    private String patientNom;
    private String medecinNom;
    private String medicament;
    private String dose;
    private String dateDebut;
    private String dateFin;

    public Traitement(int id, int patientId, int medecinId, String patientNom, String medecinNom, String medicament, String dose, String dateDebut, String dateFin) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.patientNom = patientNom;
        this.medecinNom = medecinNom;
        this.medicament = medicament;
        this.dose = dose;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters & setters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getMedecinId() { return medecinId; }
    public String getPatientNom() { return patientNom; }
    public String getMedecinNom() { return medecinNom; }
    public String getMedicament() { return medicament; }
    public String getDose() { return dose; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }

    public void setId(int id) { this.id = id; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setMedecinId(int medecinId) { this.medecinId = medecinId; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public void setMedecinNom(String medecinNom) { this.medecinNom = medecinNom; }
    public void setMedicament(String medicament) { this.medicament = medicament; }
    public void setDose(String dose) { this.dose = dose; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }
}
