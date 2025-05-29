package module; // Indique que cette classe fait partie du package 'module'

/**
 * Classe représentant un médecin.
 * Contient les informations de base : id, nom, prénom et spécialité.
 */
public class Medecin {

    // Attributs privés représentant les propriétés du médecin
    private int id; // Identifiant unique du médecin
    private String nom; // Nom de famille du médecin
    private String prenom; // Prénom du médecin
    private String specialite; // Spécialité médicale du médecin

    /**
     * Constructeur sans argument requis par JavaFX et certains frameworks
     */
    public Medecin() {}

    /**
     * Constructeur complet permettant de créer un médecin avec toutes ses informations
     *
     * @param id identifiant unique du médecin
     * @param nom nom de famille du médecin
     * @param prenom prénom du médecin
     * @param specialite spécialité du médecin
     */
    public Medecin(int id, String nom, String prenom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
    }

    // === Getters et Setters pour chaque propriété ===

    public int getId() {
        return id; // Retourne l'identifiant du médecin
    }

    public void setId(int id) {
        this.id = id; // Définit l'identifiant du médecin
    }

    public String getNom() {
        return nom; // Retourne le nom du médecin
    }

    public void setNom(String nom) {
        this.nom = nom; // Définit le nom du médecin
    }

    public String getPrenom() {
        return prenom; // Retourne le prénom du médecin
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom; // Définit le prénom du médecin
    }

    public String getSpecialite() {
        return specialite; // Retourne la spécialité du médecin
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite; // Définit la spécialité du médecin
    }
}
