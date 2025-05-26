package controller;

import com.example.projet.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    private final String CODE_CORRECT = "douaemanal";

    @FXML
    private void handleLogin() {
        String enteredPassword = passwordField.getText();

        if (CODE_CORRECT.equals(enteredPassword)) {
            // Récupérer la fenêtre actuelle (login)
            Stage currentStage = (Stage) passwordField.getScene().getWindow();

            // Ouvrir la fenêtre principale
            Main.showMainApp(currentStage);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Mot de passe incorrect !");
            alert.showAndWait();
        }
    }
}