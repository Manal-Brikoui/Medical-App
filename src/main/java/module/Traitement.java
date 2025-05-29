package module;

/**
 * Représente un traitement médical associé à un patient et un médecin.
 */
public class Traitement {
    private int id;                // Identifiant unique du traitement
    private int patientId;         // Identifiant du patient concerné
    private int medecinId;         // Identifiant du médecin concerné
    private String patientNom;     // Nom du patient (pour affichage simplifié)
    private String medecinNom;     // Nom du médecin (pour affichage simplifié)
    private String medicament;     // Nom du médicament prescrit
    private String dose;           // Dose du médicament
    private String dateDebut;      // Date de début du traitement (format chaîne)
    private String dateFin;        // Date de fin du traitement (format chaîne)

    /**
     * Constructeur complet pour initialiser un traitement.
     * @param id identifiant unique
     * @param patientId identifiant du patient
     * @param medecinId identifiant du médecin
     * @param patientNom nom du patient
     * @param medecinNom nom du médecin
     * @param medicament médicament prescrit
     * @param dose dose du médicament
     * @param dateDebut date de début au format String
     * @param dateFin date de fin au format String
     */
    public Traitement(int id, int patientId, int medecinId, String patientNom, String medecinNom,
                      String medicament, String dose, String dateDebut, String dateFin) {
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

    // Getters

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getMedecinId() { return medecinId; }
    public String getPatientNom() { return patientNom; }
    public String getMedecinNom() { return medecinNom; }
    public String getMedicament() { return medicament; }
    public String getDose() { return dose; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }

    // Setters

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
