package module;

/*package module;

import javafx.beans.property.*;

public class Patient {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    private final StringProperty dateNaissance = new SimpleStringProperty();
    private final StringProperty numCarteNationale = new SimpleStringProperty();
    private final StringProperty doseMedicament = new SimpleStringProperty();
    private final StringProperty medicaments = new SimpleStringProperty();
    private final StringProperty medecinsSuivi = new SimpleStringProperty();

    private static int counter = 1; // pour simuler l'auto-incrémentation

    public Patient(String nom, String prenom, String dateNaissance, String cin, String dose, String medicaments, String medecins) {
        this.id.set(counter++);
        this.nom.set(nom);
        this.prenom.set(prenom);
        this.dateNaissance.set(dateNaissance);
        this.numCarteNationale.set(cin);
        this.doseMedicament.set(dose);
        this.medicaments.set(medicaments);
        this.medecinsSuivi.set(medecins);
    }

    // Getters JavaFX
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public StringProperty dateNaissanceProperty() { return dateNaissance; }
    public StringProperty numCarteNationaleProperty() { return numCarteNationale; }

    // Pour accès normal (optionnel)
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public String getDateNaissance() { return dateNaissance.get(); }
    public String getNumCarteNationale() { return numCarteNationale.get(); }
}*/
/*package module;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String carteNationale;
    private String medecin;
    private String traitement;
    private String etatVisite;

    public Patient() {}

    public Patient(int id, String nom, String prenom, String dateNaissance, String carteNationale, String medecin, String traitement, String etatVisite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.carteNationale = carteNationale;
        this.medecin = medecin;
        this.traitement = traitement;
        this.etatVisite = etatVisite;
    }

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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCarteNationale() {
        return carteNationale;
    }

    public void setCarteNationale(String carteNationale) {
        this.carteNationale = carteNationale;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public String getEtatVisite() {
        return etatVisite;
    }

    public void setEtatVisite(String etatVisite) {
        this.etatVisite = etatVisite;
    }
}*/
/*public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String telephone;

    // Constructeur par défaut
    public Patient() {
    }

    // Constructeur complet (optionnel, utile si tu veux instancier directement)
    public Patient(int id, String nom, String prenom, int age, String sexe, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.telephone = telephone;
    }

    // Getters et Setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Optionnel : utile pour debug
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", sexe='" + sexe + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}*/

import java.time.LocalDate;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numCarteNationale;
    private String doseMedicament;
    private String medicaments;
    private String medecinsSuivi;

    public Patient() {}

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

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getNumCarteNationale() { return numCarteNationale; }
    public void setNumCarteNationale(String numCarteNationale) { this.numCarteNationale = numCarteNationale; }
    public String getDoseMedicament() { return doseMedicament; }
    public void setDoseMedicament(String doseMedicament) { this.doseMedicament = doseMedicament; }
    public String getMedicaments() { return medicaments; }
    public void setMedicaments(String medicaments) { this.medicaments = medicaments; }
    public String getMedecinsSuivi() { return medecinsSuivi; }
    public void setMedecinsSuivi(String medecinsSuivi) { this.medecinsSuivi = medecinsSuivi; }

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