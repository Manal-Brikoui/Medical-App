package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import module.Medecin;
import module.MedecinDAO;
import javafx.scene.layout.*;

import java.util.Optional;

public class MedecinController {

    // Colonnes de la TableView
    @FXML private TableView<Medecin> medecinTable;
    @FXML private TableColumn<Medecin, Integer> idColumn;
    @FXML private TableColumn<Medecin, String> nomColumn;
    @FXML private TableColumn<Medecin, String> prenomColumn;
    @FXML private TableColumn<Medecin, String> specialiteColumn;
    @FXML private TableColumn<Medecin, Void> actionColumn;

    // Champ de recherche et bouton d'ajout
    @FXML private TextField searchField;
    @FXML private Button addButton;

    // Liste observable des médecins
    private ObservableList<Medecin> medecinList;
    private FilteredList<Medecin> filteredData;

    // Méthode d'initialisation automatique appelée par JavaFX
    @FXML
    public void initialize() {
        // Lier les colonnes de la TableView aux propriétés de la classe Medecin
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        loadMedecins();          // Charger les médecins depuis la base
        setupSearchFilter();     // Activer la recherche dynamique
        addButtonToTable();      // Ajouter les boutons "Modifier" et "Supprimer"

        // Action du bouton "Ajouter"
        addButton.setOnAction(e -> openAddMedecinDialog());
    }

    // Charger tous les médecins depuis la base de données via DAO
    private void loadMedecins() {
        MedecinDAO dao = new MedecinDAO();
        medecinList = FXCollections.observableArrayList(dao.getAllMedecins());
    }

    // Mettre en place un filtre dynamique en fonction de la recherche
    private void setupSearchFilter() {
        filteredData = new FilteredList<>(medecinList, p -> true); // filtre initial : aucun
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(m -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String ft = newVal.toLowerCase();
                return m.getNom().toLowerCase().contains(ft)
                        || m.getPrenom().toLowerCase().contains(ft)
                        || (m.getSpecialite() != null && m.getSpecialite().toLowerCase().contains(ft));
            });
        });

        // Tris synchronisés avec la table
        SortedList<Medecin> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(medecinTable.comparatorProperty());
        medecinTable.setItems(sortedData);
    }

    // Ajouter des boutons "Modifier" et "Supprimer" dans chaque ligne
    private void addButtonToTable() {
        Callback<TableColumn<Medecin, Void>, TableCell<Medecin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final VBox buttonBox = new VBox(5, btnEdit, btnDelete); // boutons verticaux

            {
                // Styles CSS inline
                btnEdit.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                // Action du bouton "Modifier"
                btnEdit.setOnAction(ev -> {
                    Medecin m = getTableView().getItems().get(getIndex());
                    openEditMedecinDialog(m);
                });

                // Action du bouton "Supprimer"
                btnDelete.setOnAction(ev -> {
                    Medecin m = getTableView().getItems().get(getIndex());
                    new MedecinDAO().deleteMedecinById(m.getId());
                    medecinList.remove(m);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonBox);
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    // Ouvrir une boîte de dialogue pour ajouter un médecin
    private void openAddMedecinDialog() {
        Dialog<Medecin> dialog = createMedecinDialog(null);
        Optional<Medecin> res = dialog.showAndWait();
        res.ifPresent(m -> {
            new MedecinDAO().insertMedecin(m);
            medecinList.add(m);
            searchField.clear(); // Réinitialiser la recherche
            filteredData.setPredicate(med -> true);
            medecinTable.refresh();
        });
    }

    // Ouvrir une boîte de dialogue pour modifier un médecin
    private void openEditMedecinDialog(Medecin medecin) {
        Dialog<Medecin> dialog = createMedecinDialog(medecin);
        Optional<Medecin> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            // Mise à jour des données locales
            medecin.setNom(updated.getNom());
            medecin.setPrenom(updated.getPrenom());
            medecin.setSpecialite(updated.getSpecialite());

            // Mise à jour en base
            new MedecinDAO().updateMedecin(medecin);
            medecinTable.refresh();
        });
    }

    // Crée un Dialog (ajout ou édition)
    private Dialog<Medecin> createMedecinDialog(Medecin existingMedecin) {
        Dialog<Medecin> dialog = new Dialog<>();
        dialog.setTitle(existingMedecin == null ? "Ajouter un Médecin" : "Modifier un Médecin");
        ButtonType actionType = new ButtonType(existingMedecin == null ? "Ajouter" : "Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Champs de saisie
        TextField nomF = new TextField();
        TextField prenomF = new TextField();
        TextField specialiteF = new TextField();

        // Pré-remplissage si on modifie
        if (existingMedecin != null) {
            nomF.setText(existingMedecin.getNom());
            prenomF.setText(existingMedecin.getPrenom());
            specialiteF.setText(existingMedecin.getSpecialite());
        }

        // Placement dans la grille
        grid.addRow(0, new Label("Nom:"), nomF);
        grid.addRow(1, new Label("Prénom:"), prenomF);
        grid.addRow(2, new Label("Spécialité:"), specialiteF);

        dialog.getDialogPane().setContent(grid);

        // Désactiver le bouton si le champ nom est vide
        Node actionBtn = dialog.getDialogPane().lookupButton(actionType);
        actionBtn.setDisable(nomF.getText().trim().isEmpty());
        nomF.textProperty().addListener((o, ov, nv) -> actionBtn.setDisable(nv.trim().isEmpty()));

        // Conversion du résultat du formulaire en objet Medecin
        dialog.setResultConverter(bt -> {
            if (bt == actionType) {
                return new Medecin(
                        existingMedecin != null ? existingMedecin.getId() : 0,
                        nomF.getText(),
                        prenomF.getText(),
                        specialiteF.getText()
                );
            }
            return null;
        });

        return dialog;
    }

}
