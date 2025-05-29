package module;
import java.time.LocalDate; // Import pour gérer les dates (date de naissance ici)

public class Patient {
    // Attributs privés de la classe Patient
    private int id;                      // Identifiant unique du patient
    private String nom;                  // Nom du patient
    private String prenom;               // Prénom du patient
    private LocalDate dateNaissance;    // Date de naissance (type LocalDate pour gérer les dates facilement)
    private String numCarteNationale;   // Numéro de la carte nationale d'identité du patient
    private String doseMedicament;      // Dose du médicament prise par le patient
    private String medicaments;         // Médicaments prescrits au patient (stockés sous forme de texte)
    private String medecinsSuivi;       // Nom(s) des médecins qui suivent le patient (stockés sous forme de texte)

    // Constructeur par défaut, utile pour instancier un patient vide puis remplir ses champs
    public Patient() {}

    // Constructeur complet avec tous les champs en paramètres
    public Patient(int id, String nom, String prenom, LocalDate dateNaissance,
                   String numCarteNationale, String doseMedicament,
                   String medicaments, String medecinsSuivi) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numCarteNationale = numCarteNationale;
        this.doseMedicament = doseMedicament;
        this.medicaments = medicaments;
        this.medecinsSuivi = medecinsSuivi;
    }

    // --- Getters et Setters ---
    // Permettent d'accéder et modifier les attributs de la classe en respectant l'encapsulation

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumCarteNationale() {
        return numCarteNationale;
    }

    public void setNumCarteNationale(String numCarteNationale) {
        this.numCarteNationale = numCarteNationale;
    }

    public String getDoseMedicament() {
        return doseMedicament;
    }

    public void setDoseMedicament(String doseMedicament) {
        this.doseMedicament = doseMedicament;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public String getMedecinsSuivi() {
        return medecinsSuivi;
    }

    public void setMedecinsSuivi(String medecinsSuivi) {
        this.medecinsSuivi = medecinsSuivi;
    }

    // Redéfinition de la méthode toString() pour afficher un Patient dans un format lisible
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", numCarteNationale='" + numCarteNationale + '\'' +
                ", doseMedicament='" + doseMedicament + '\'' +
                ", medicaments='" + medicaments + '\'' +
                ", medecinsSuivi='" + medecinsSuivi + '\'' +
                '}';
    }
}
