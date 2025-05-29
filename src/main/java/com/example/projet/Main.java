package com.example.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    // Layout principal de l'application (avec barre de menu en haut et contenu au centre)
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement de la vue de connexion (LoginView.fxml)
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/LoginView.fxml")));

            // Création et affichage de la scène de connexion
            Scene scene = new Scene(loginRoot);
            primaryStage.setTitle("Connexion Sécurisée");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode statique appelée après une connexion réussie pour afficher l'interface principale
     */
    public static void showMainApp(Stage stage) {
        // Création du layout principal avec menu en haut et vue au centre
        rootLayout = new BorderPane();

        // Création de la barre de menu horizontale
        HBox menuBar = new HBox(10);
        menuBar.setStyle("-fx-background-color: #0e8f95; -fx-padding: 10;");

        // Création des boutons du menu
        Button btnPatients = new Button("Patients");
        Button btnMedecins = new Button("Médecins");
        Button btnTraitements = new Button("Traitements");
        Button btnVisites = new Button("Visites");

        // Application du style commun aux boutons
        String btnStyle = "-fx-background-color: white; -fx-text-fill: #0e8f95; -fx-font-weight: bold;";
        btnPatients.setStyle(btnStyle);
        btnMedecins.setStyle(btnStyle);
        btnTraitements.setStyle(btnStyle);
        btnVisites.setStyle(btnStyle);

        // Ajout des boutons dans la barre de menu
        menuBar.getChildren().addAll(btnPatients, btnMedecins, btnTraitements, btnVisites);
        rootLayout.setTop(menuBar); // Placement en haut de la fenêtre

        // Chargement initial de la vue "Patients"
        loadView("PatientView.fxml");

        // Gestion des clics sur les boutons pour changer la vue au centre
        btnPatients.setOnAction(e -> loadView("PatientView.fxml"));
        btnMedecins.setOnAction(e -> loadView("MedecinVIEW.fxml"));
        btnTraitements.setOnAction(e -> loadView("TraitementView.fxml"));
        btnVisites.setOnAction(e -> loadView("VisiteView.fxml"));

        // Création de la scène principale et affichage
        Scene mainScene = new Scene(rootLayout, 1000, 700);
        stage.setTitle("MedicalTrack - Application Médicale");
        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * Méthode utilitaire pour charger dynamiquement une vue FXML dans le centre du layout
     * @param fxmlFile Nom du fichier FXML à charger
     */
    private static void loadView(String fxmlFile) {
        try {
            // Chargement du fichier FXML à partir du chemin des ressources
            Parent view = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/example/projet/" + fxmlFile)));
            rootLayout.setCenter(view); // Affichage au centre de la fenêtre
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur en cas de problème de chargement
        }
    }

    public static void main(String[] args) {
        launch(args); // Lancement de l'application JavaFX
    }
}
