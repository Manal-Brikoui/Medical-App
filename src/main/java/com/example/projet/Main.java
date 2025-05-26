/*package com.example.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            TabPane tabPane = new TabPane();

            Parent patientPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/PatientVIEW.fxml")));
            Tab patientTab = new Tab("Patients", patientPane);

            Parent medecinPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/MedecinVIEW.fxml")));
            Tab medecinTab = new Tab("Médecins", medecinPane);

            Parent traitementPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/TraitementVIEW.fxml")));
            Tab traitementTab = new Tab("Traitements", traitementPane);

            Parent visitePane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/VisiteVIEW.fxml")));
            Tab visiteTab = new Tab("Visites", visitePane);

            tabPane.getTabs().addAll(patientTab, medecinTab, traitementTab, visiteTab);

            Scene scene = new Scene(tabPane, 1000, 700);
            primaryStage.setTitle("MedicalTrack - Application Médicale");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/
/*package com.example.medicalapp;

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

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Création de la BorderPane principale
        rootLayout = new BorderPane();

        // Création du menu en haut
        HBox menuBar = new HBox(10);
        menuBar.setStyle("-fx-background-color: #0e8f95; -fx-padding: 10;");

        Button btnPatients = new Button("Patients");
        Button btnMedecins = new Button("Médecins");
        Button btnTraitements = new Button("Traitements");
        Button btnVisites = new Button("Visites");

        // Style des boutons
        String btnStyle = "-fx-background-color: white; -fx-text-fill: #0e8f95; -fx-font-weight: bold;";
        btnPatients.setStyle(btnStyle);
        btnMedecins.setStyle(btnStyle);
        btnTraitements.setStyle(btnStyle);
        btnVisites.setStyle(btnStyle);

        menuBar.getChildren().addAll(btnPatients, btnMedecins, btnTraitements, btnVisites);

        // Ajout menu en haut
        rootLayout.setTop(menuBar);

        // Chargement initial de la vue Patients (avec chemin corrigé)
        loadView("PatientView.fxml");

        // Actions boutons pour changer la vue centrale (avec chemins corrigés)
        btnPatients.setOnAction(e -> loadView("PatientView.fxml"));
        btnMedecins.setOnAction(e -> loadView("MedecinVIEW.fxml"));
        btnTraitements.setOnAction(e -> loadView("TraitementView.fxml"));
        btnVisites.setOnAction(e -> loadView("VisiteView.fxml"));


        Scene scene = new Scene(rootLayout, 1000, 700);
        primaryStage.setTitle("MedicalTrack - Application Médicale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode utilitaire pour charger une vue FXML dans le centre de rootLayout
    private void loadView(String fxmlFile) {
        try {
            // Chemin correct selon ta structure de ressources
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/medicalapp/" + fxmlFile)));
            rootLayout.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/
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

    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/projet/LoginView.fxml")));
            Scene scene = new Scene(loginRoot);
            primaryStage.setTitle("Connexion Sécurisée");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher l'interface principale après connexion
    public static void showMainApp(Stage stage) {
        rootLayout = new BorderPane();

        // Création du menu en haut
        HBox menuBar = new HBox(10);
        menuBar.setStyle("-fx-background-color: #0e8f95; -fx-padding: 10;");

        Button btnPatients = new Button("Patients");
        Button btnMedecins = new Button("Médecins");
        Button btnTraitements = new Button("Traitements");
        Button btnVisites = new Button("Visites");

        // Style des boutons
        String btnStyle = "-fx-background-color: white; -fx-text-fill: #0e8f95; -fx-font-weight: bold;";
        btnPatients.setStyle(btnStyle);
        btnMedecins.setStyle(btnStyle);
        btnTraitements.setStyle(btnStyle);
        btnVisites.setStyle(btnStyle);

        menuBar.getChildren().addAll(btnPatients, btnMedecins, btnTraitements, btnVisites);
        rootLayout.setTop(menuBar);

        // Chargement initial
        loadView("PatientView.fxml");

        // Actions des boutons
        btnPatients.setOnAction(e -> loadView("PatientView.fxml"));
        btnMedecins.setOnAction(e -> loadView("MedecinVIEW.fxml"));
        btnTraitements.setOnAction(e -> loadView("TraitementView.fxml"));
        btnVisites.setOnAction(e -> loadView("VisiteView.fxml"));

        Scene mainScene = new Scene(rootLayout, 1000, 700);
        stage.setTitle("MedicalTrack - Application Médicale");
        stage.setScene(mainScene);
        stage.show();
    }

    // Méthode utilitaire pour charger les vues dans le centre de rootLayout
    private static void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/example/projet/" + fxmlFile)));
            rootLayout.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}