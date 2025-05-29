package controller;

import com.example.projet.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {

    // Champ pour saisir le mot de passe dans l'interface LoginView.fxml
    @FXML
    private PasswordField passwordField;

    // Mot de passe attendu pour accéder à l'application
    private final String CODE_CORRECT = "douaemanal";

    // Méthode appelée lorsqu'on clique sur le bouton "Se connecter"
    @FXML
    private void handleLogin() {
        // Récupération du mot de passe saisi par l'utilisateur
        String enteredPassword = passwordField.getText();

        // Vérification du mot de passe
        if (CODE_CORRECT.equals(enteredPassword)) {
            // Si le mot de passe est correct, fermer la fenêtre actuelle (login)
            Stage currentStage = (Stage) passwordField.getScene().getWindow();

            // Et ouvrir l'application principale avec les vues (Patients, Médecins, etc.)
            Main.showMainApp(currentStage);

        } else {
            // Si le mot de passe est incorrect, afficher une alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null); // Pas de titre secondaire
            alert.setContentText("Mot de passe incorrect !");
            alert.showAndWait(); // Affichage de l'alerte et attente de la fermeture
        }
    }
}
