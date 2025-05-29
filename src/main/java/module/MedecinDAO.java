package module; // Indique que cette classe appartient au package 'module'

import java.sql.*; // Pour utiliser les classes JDBC : Connection, Statement, PreparedStatement, ResultSet
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) pour la gestion des objets Medecin en base de données.
 * Fournit des méthodes pour lire, insérer, mettre à jour et supprimer des médecins.
 */
public class MedecinDAO {

    /**
     * Méthode utilitaire pour établir une connexion à la base de données.
     * @return une connexion JDBC valide.
     * @throws SQLException en cas de problème de connexion.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/traitement", "root", "");
        // URL = base de données MySQL locale, nom de la base : traitement, utilisateur : root, mot de passe : vide
    }

    /**
     * Récupère la liste complète des médecins depuis la base de données.
     * @return liste d'objets Medecin.
     */
    public List<Medecin> getAllMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin"; // Requête SQL

        try (
                Connection conn = getConnection(); // Connexion automatique (try-with-resources)
                Statement stmt = conn.createStatement(); // Création d'un statement
                ResultSet rs = stmt.executeQuery(sql) // Exécution de la requête
        ) {
            while (rs.next()) {
                Medecin m = new Medecin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialite")
                );
                medecins.add(m); // Ajout de l'objet Medecin à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affichage de l'erreur en cas d'échec
        }

        return medecins; // Renvoie la liste des médecins
    }

    /**
     * Insère un nouveau médecin dans la base de données.
     * @param m l'objet Medecin à insérer.
     */
    public void insertMedecin(Medecin m) {
        String sql = "INSERT INTO medecin(nom, prenom, specialite) VALUES (?, ?, ?)";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Paramétrage des valeurs
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());

            ps.executeUpdate(); // Exécution de la requête

            // Récupération de l'ID généré automatiquement (auto_increment)
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setId(generatedKeys.getInt(1)); // Mise à jour de l'id dans l'objet Medecin
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un médecin existant.
     * @param m le médecin à mettre à jour.
     */
    public void updateMedecin(Medecin m) {
        String sql = "UPDATE medecin SET nom=?, prenom=?, specialite=? WHERE id=?";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setInt(4, m.getId());

            ps.executeUpdate(); // Exécution de la mise à jour
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un médecin de la base en fonction de son identifiant.
     * @param id identifiant du médecin à supprimer.
     */
    public void deleteMedecinById(int id) {
        String sql = "DELETE FROM medecin WHERE id=?";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate(); // Exécution de la suppression
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
