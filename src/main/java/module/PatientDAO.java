package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Méthode privée pour obtenir une connexion à la base de données
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/traitement";  // URL de la base de données
        String user = "root";                                  // Nom d'utilisateur
        String password = "";                                  // Mot de passe
        return DriverManager.getConnection(url, user, password);  // Connexion établie
    }

    /**
     * Récupère tous les patients dans la base de données.
     * @return Liste des patients
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();            // Liste pour stocker les patients récupérés
        String sql = "SELECT * FROM patient";                  // Requête SQL pour sélectionner tous les patients

        try (Connection conn = getConnection();                // Ouverture connexion
             Statement stmt = conn.createStatement();          // Création d'un statement
             ResultSet rs = stmt.executeQuery(sql)) {          // Exécution de la requête

            while (rs.next()) {                                // Parcours des résultats
                // Création d'un nouvel objet Patient à partir des données récupérées
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance").toLocalDate(),   // Conversion SQL Date -> LocalDate
                        rs.getString("num_carte_nationale"),
                        rs.getString("dose_medicament"),
                        rs.getString("medicaments"),
                        rs.getString("medecins_suivi")
                );
                patients.add(p);                                // Ajout du patient à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace();                                // En cas d'erreur, affichage de la stack trace
        }

        return patients;                                       // Retourne la liste complète des patients
    }

    /**
     * Insère un nouveau patient dans la base de données.
     * @param p Patient à insérer
     */
    public void insertPatient(Patient p) {
        String sql = "INSERT INTO patient (nom, prenom, date_naissance, num_carte_nationale, dose_medicament, medicaments, medecins_suivi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Attribution des valeurs dans la requête préparée
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));    // Conversion LocalDate -> java.sql.Date
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());

            ps.executeUpdate();                                   // Exécution de l'insertion

            // Récupération de l'id auto-généré et mise à jour de l'objet Patient
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();                                  // Affichage d'erreur en cas d'exception
        }
    }

    /**
     * Supprime un patient dans la base par son identifiant.
     * @param id Identifiant du patient à supprimer
     */
    public void deletePatientById(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);                                     // Spécifie l'id du patient à supprimer
            ps.executeUpdate();                                   // Exécution de la suppression

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un patient existant.
     * @param p Patient avec les nouvelles données
     */
    public void updatePatient(Patient p) {
        String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, num_carte_nationale = ?, dose_medicament = ?, medicaments = ?, medecins_suivi = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Attribution des nouvelles valeurs
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.setInt(8, p.getId());                              // Identification du patient à mettre à jour

            ps.executeUpdate();                                   // Exécution de la mise à jour

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
