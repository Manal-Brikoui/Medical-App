
/*package module;

import module.Medecin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/traitement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Medecin> getAllMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM  medecin";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medecins.add(new Medecin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialite")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }
}*/

package module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    private Connection getConnection() throws SQLException {
        // Remplace par ta connexion JDBC réelle
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/traitement", "root", "");
    }

    public List<Medecin> getAllMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medecin m = new Medecin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialite")
                );
                medecins.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }

    public void insertMedecin(Medecin m) {
        String sql = "INSERT INTO medecin(nom, prenom, specialite) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMedecin(Medecin m) {
        String sql = "UPDATE medecin SET nom=?, prenom=?, specialite=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setInt(4, m.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedecinById(int id) {
        String sql = "DELETE FROM medecin WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
