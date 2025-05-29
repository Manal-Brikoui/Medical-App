package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VisiteDAO {

    // URL, utilisateur et mot de passe pour la connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/traitement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour obtenir une connexion JDBC
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Récupère toutes les visites avec le nom du patient associé.
     * Utilise une jointure SQL entre la table visite et patient.
     *
     * @return Liste des visites complètes avec patientNom
     */
    public List<Visite> getAllVisites() {
        List<Visite> visites = new ArrayList<>();
        String sql = "SELECT v.id, v.patient_id, p.nom AS patientNom, v.date_visite, v.etat_rendu " +
                "FROM visite v " +
                "JOIN patient p ON v.patient_id = p.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Visite v = new Visite(
                        rs.getInt("id"),                              // id visite
                        rs.getInt("patient_id"),                      // id patient
                        rs.getString("patientNom"),                   // nom patient
                        rs.getDate("date_visite").toLocalDate(),     // conversion SQL Date -> LocalDate
                        rs.getString("etat_rendu")                    // état rendu
                );
                visites.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visites;
    }

    /**
     * Insère une nouvelle visite en base.
     * Le champ id est auto-généré et récupéré dans l'objet Visite passé en paramètre.
     *
     * @param visite Objet Visite à insérer
     * @return true si insertion réussie, false sinon
     */
    public boolean insertVisite(Visite visite) {
        String sql = "INSERT INTO visite(patient_id, date_visite, etat_rendu) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, visite.getPatientId());
            stmt.setDate(2, Date.valueOf(visite.getDateVisite()));  // Conversion LocalDate -> java.sql.Date
            stmt.setString(3, visite.getEtatRendu());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Récupérer la clé générée (id)
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    visite.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Met à jour une visite existante en base selon son id.
     *
     * @param visite Objet Visite avec id à modifier
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateVisite(Visite visite) {
        String sql = "UPDATE visite SET patient_id = ?, date_visite = ?, etat_rendu = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visite.getPatientId());
            stmt.setDate(2, Date.valueOf(visite.getDateVisite()));
            stmt.setString(3, visite.getEtatRendu());
            stmt.setInt(4, visite.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime une visite en base selon son id.
     *
     * @param id Identifiant de la visite à supprimer
     * @return true si suppression réussie, false sinon
     */
    public boolean deleteVisite(int id) {
        String sql = "DELETE FROM visite WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remarque : pour remplir une ComboBox patients dans l'interface,
    // il faudrait une méthode getAllPatients() dans une classe PatientDAO.
}
