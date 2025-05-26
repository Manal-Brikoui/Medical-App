
/*package module;

import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private static final List<Patient> patients = new ArrayList<>();

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public void ajouterPatient(Patient patient) {
        patients.add(patient);
    }

    public void supprimerPatient(int id) {
        patients.removeIf(p -> p.getId() == id);
    }
}*/
/*package module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/traitement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, age, sexe, telephone FROM patient";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
                patient.setAge(rs.getInt("age"));
                patient.setSexe(rs.getString("sexe"));
                patient.setTelephone(rs.getString("telephone"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Tu peux ajouter ici les méthodes insert/update/delete si nécessaire
}*/
/*package module;

import module.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private static Connection getConnection() throws SQLException {
        // Remplace cette ligne par ta propre méthode de connexion
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_ta_base", "utilisateur", "mot_de_passe");
    }

    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
                patient.setAge(rs.getInt("age"));
                patient.setSexe(rs.getString("sexe"));
                patient.setTelephone(rs.getString("telephone"));
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static void deletePatient(int id) {
        String query = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ajouterPatient(Patient patient) {
        String query = "INSERT INTO patients (nom, prenom, age, sexe, telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getSexe());
            stmt.setString(5, patient.getTelephone());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifierPatient(Patient patient) {
        String query = "UPDATE patients SET nom = ?, prenom = ?, age = ?, sexe = ?, telephone = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getSexe());
            stmt.setString(5, patient.getTelephone());
            stmt.setInt(6, patient.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}*/
package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/traitement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance").toLocalDate(),
                        rs.getString("num_carte_nationale"),
                        rs.getString("dose_medicament"),
                        rs.getString("medicaments"),
                        rs.getString("medecins_suivi")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void insertPatient(Patient p) {
        String sql = "INSERT INTO patient (nom, prenom, date_naissance, num_carte_nationale, dose_medicament, medicaments, medecins_suivi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatientById(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(Patient p) {
        String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, num_carte_nationale = ?, dose_medicament = ?, medicaments = ?, medecins_suivi = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
