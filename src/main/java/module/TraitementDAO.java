package module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TraitementDAO {

    // Méthode pour obtenir une connexion à la base de données MySQL
    private Connection getConnection() throws SQLException {
        // URL de connexion, utilisateur, mot de passe
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/traitement", "root", "");
    }

    // Récupère la liste complète des traitements avec infos patients et médecins
    public List<Traitement> getAllTraitements() {
        List<Traitement> traitements = new ArrayList<>();

        // Requête SQL avec jointures pour obtenir noms et ids des patients et médecins
        String sql = "SELECT t.id, p.id AS patient_id, p.nom AS patient_nom, " +
                "m.id AS medecin_id, m.nom AS medecin_nom, " +
                "t.medicament, t.dose, t.date_debut, t.date_fin " +
                "FROM traitement t " +
                "JOIN Patient p ON t.patient_id = p.id " +
                "JOIN Medecin m ON t.medecin_id = m.id";

        // try-with-resources pour assurer la fermeture automatique des ressources
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours de chaque ligne du résultat
            while (rs.next()) {
                Traitement t = new Traitement(
                        rs.getInt("id"),                     // id du traitement
                        rs.getInt("patient_id"),             // id patient
                        rs.getInt("medecin_id"),             // id médecin
                        rs.getString("patient_nom"),         // nom patient
                        rs.getString("medecin_nom"),         // nom médecin
                        rs.getString("medicament"),          // médicament
                        rs.getString("dose"),                 // dose
                        rs.getDate("date_debut").toString(), // date début (convertie en String)
                        rs.getDate("date_fin") != null ? rs.getDate("date_fin").toString() : "" // date fin ou vide
                );
                traitements.add(t); // ajout à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace(); // afficher l'erreur SQL en cas d'exception
        }

        return traitements; // retourner la liste complète
    }

    // Méthode pour insérer un nouveau traitement en base
    public boolean addTraitement(Traitement t) {
        String sql = "INSERT INTO traitement (patient_id, medecin_id, medicament, dose, date_debut, date_fin) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // On remplit les paramètres de la requête préparée
            ps.setInt(1, t.getPatientId());
            ps.setInt(2, t.getMedecinId());
            ps.setString(3, t.getMedicament());
            ps.setString(4, t.getDose());
            ps.setDate(5, Date.valueOf(t.getDateDebut())); // conversion String -> java.sql.Date

            // gestion de la dateFin qui peut être vide ou nulle
            if (t.getDateFin() == null || t.getDateFin().isEmpty()) {
                ps.setNull(6, Types.DATE); // valeur SQL NULL pour date_fin
            } else {
                ps.setDate(6, Date.valueOf(t.getDateFin()));
            }

            // Exécution de la requête d'insertion (retourne nb lignes modifiées)
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // affiche erreur SQL
            return false; // insertion échouée
        }
    }

    // Méthode pour mettre à jour un traitement existant en base
    public boolean updateTraitement(Traitement t) {
        String sql = "UPDATE traitement SET patient_id = ?, medecin_id = ?, medicament = ?, dose = ?, date_debut = ?, date_fin = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // remplissage des paramètres (similaire à addTraitement)
            ps.setInt(1, t.getPatientId());
            ps.setInt(2, t.getMedecinId());
            ps.setString(3, t.getMedicament());
            ps.setString(4, t.getDose());
            ps.setDate(5, Date.valueOf(t.getDateDebut()));

            if (t.getDateFin() == null || t.getDateFin().isEmpty()) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(t.getDateFin()));
            }

            ps.setInt(7, t.getId()); // id du traitement à mettre à jour

            // Exécution de la mise à jour, true si au moins une ligne modifiée
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // mise à jour échouée
        }
    }

    // Méthode pour supprimer un traitement par son id
    public boolean deleteTraitement(int id) {
        String sql = "DELETE FROM traitement WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // on précise l'id à supprimer

            // Exécution de la suppression, true si au moins une ligne supprimée
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // suppression échouée
        }
    }
}
