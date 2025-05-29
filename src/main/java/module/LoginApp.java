package module; // Déclare le package dans lequel se trouve cette classe

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale qui lance l'application JavaFX.
 * Cette classe affiche la fenêtre de connexion (Login) au démarrage.
 */
public class LoginApp extends Application {

    /**
     * Méthode appelée automatiquement lors du lancement de l'application JavaFX.
     * Elle charge l'interface de connexion définie dans le fichier FXML et l'affiche dans une fenêtre.
     *
     * @param primaryStage la fenêtre principale (Stage) de l'application.
     * @throws Exception en cas d'erreur de chargement du fichier FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charge le fichier FXML de l'interface de connexion
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medicalapp/login.fxml"));

        // Définit le titre de la fenêtre
        primaryStage.setTitle("Connexion");

        // Crée une scène avec le contenu chargé et définit ses dimensions (300x150 pixels)
        primaryStage.setScene(new Scene(root, 300, 150));

        // Affiche la fenêtre
        primaryStage.show();
    }

    /**
     * Point d'entrée de l'application.
     * La méthode launch() initialise l'application JavaFX.
     *
     * @param args arguments de ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}
