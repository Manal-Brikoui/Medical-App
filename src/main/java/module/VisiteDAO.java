package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VisiteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/traitement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Récupère toutes les visites avec nom patient
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
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("patientNom"),
                        rs.getDate("date_visite").toLocalDate(),
                        rs.getString("etat_rendu")
                );
                visites.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visites;
    }

    // Ajouter une visite
    public boolean insertVisite(Visite visite) {
        String sql = "INSERT INTO visite(patient_id, date_visite, etat_rendu) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, visite.getPatientId());
            stmt.setDate(2, Date.valueOf(visite.getDateVisite()));
            stmt.setString(3, visite.getEtatRendu());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
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

    // Modifier une visite
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

    // Supprimer une visite
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

    // Pour remplir ComboBox patient, tu peux ajouter une méthode getAllPatients()
    // qui renvoie List<Patient> avec id et nom.
    // À adapter selon ta classe Patient et a base.
}
