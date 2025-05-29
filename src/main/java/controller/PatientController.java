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
import module.Patient;

import javafx.scene.layout.*;
import module.PatientDAO;
import java.time.LocalDate;
import java.util.Optional;

public class PatientController {

    // Déclaration des composants de l'interface graphique liés à la TableView
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, LocalDate> dateNaissColumn;
    @FXML private TableColumn<Patient, String> cnColumn;
    @FXML private TableColumn<Patient, String> doseMedColumn;
    @FXML private TableColumn<Patient, String> medicsColumn;
    @FXML private TableColumn<Patient, String> medecinColumn;
    @FXML private TableColumn<Patient, Void> actionColumn;
    @FXML private TextField searchField;
    @FXML private Button addButton;

    // Liste observable pour stocker les patients et permettre leur affichage dynamique
    private ObservableList<Patient> patientList;
    private FilteredList<Patient> filteredData;

    // Méthode appelée automatiquement à l'initialisation du contrôleur
    @FXML
    public void initialize() {
        // Liaison des colonnes de la TableView avec les propriétés de l'objet Patient
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        cnColumn.setCellValueFactory(new PropertyValueFactory<>("numCarteNationale"));
        doseMedColumn.setCellValueFactory(new PropertyValueFactory<>("doseMedicament"));
        medicsColumn.setCellValueFactory(new PropertyValueFactory<>("medicaments"));
        medecinColumn.setCellValueFactory(new PropertyValueFactory<>("medecinsSuivi"));

        // Chargement des données dans la table
        loadPatients();

        // Ajout de boutons "Modifier" et "Supprimer" dans chaque ligne de la table
        addButtonToTable();

        // Action associée au bouton "Ajouter"
        addButton.setOnAction(e -> openAddPatientDialog());
    }

    // Chargement des patients depuis la base de données
    private void loadPatients() {
        PatientDAO dao = new PatientDAO();
        patientList = FXCollections.observableArrayList(dao.getAllPatients());

        // Application d'un filtre (par défaut tout est affiché)
        filteredData = new FilteredList<>(patientList, p -> true);

        // Tri des données liées au tri de la TableView
        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());

        // Remplissage de la TableView
        patientTable.setItems(sortedData);
    }

    // Mise en place de la recherche en temps réel dans la table
    private void setupSearchFilter() {
        filteredData = new FilteredList<>(patientList, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(p -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String ft = newVal.toLowerCase();
                return p.getNom().toLowerCase().contains(ft)
                        || p.getPrenom().toLowerCase().contains(ft)
                        || p.getNumCarteNationale().toLowerCase().contains(ft);
            });
        });
        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedData);
    }

    // Ajout de boutons Modifier et Supprimer dans une colonne personnalisée
    private void addButtonToTable() {
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final VBox buttonBox = new VBox(5, btnEdit, btnDelete); // Conteneur vertical

            {
                // Style des boutons
                btnEdit.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                // Action "Modifier"
                btnEdit.setOnAction(ev -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    openEditPatientDialog(p);
                });

                // Action "Supprimer"
                btnDelete.setOnAction(ev -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    new PatientDAO().deletePatientById(p.getId());
                    patientList.remove(p); // Suppression de la liste observable
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonBox); // Affiche les boutons uniquement si la ligne n'est pas vide
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    // Ouverture d'une boîte de dialogue pour ajouter un patient
    private void openAddPatientDialog() {
        Dialog<Patient> dialog = createPatientDialog(null);
        Optional<Patient> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            new PatientDAO().insertPatient(updated); // Insertion en base
            patientList.add(updated); // Ajout dans la liste observable
            patientTable.refresh();   // Rafraîchissement de la TableView
        });
    }

    // Ouverture d'une boîte de dialogue pour modifier un patient existant
    private void openEditPatientDialog(Patient patient) {
        Dialog<Patient> dialog = createPatientDialog(patient);
        Optional<Patient> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            // Mise à jour des champs de l'objet existant
            patient.setNom(updated.getNom());
            patient.setPrenom(updated.getPrenom());
            patient.setDateNaissance(updated.getDateNaissance());
            patient.setNumCarteNationale(updated.getNumCarteNationale());
            patient.setDoseMedicament(updated.getDoseMedicament());
            patient.setMedicaments(updated.getMedicaments());
            patient.setMedecinsSuivi(updated.getMedecinsSuivi());

            new PatientDAO().updatePatient(patient); // Mise à jour dans la base
            patientTable.refresh(); // Rafraîchissement de l'affichage
        });
    }

    // Création de la boîte de dialogue pour ajouter ou modifier un patient
    private Dialog<Patient> createPatientDialog(Patient existingPatient) {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle(existingPatient == null ? "Ajouter un Patient" : "Modifier un Patient");
        ButtonType actionType = new ButtonType(existingPatient == null ? "Ajouter" : "Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        // Création du formulaire avec champs
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField nomF    = new TextField();
        TextField prenomF = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField cnF     = new TextField();
        TextField doseF   = new TextField();
        TextField medsF   = new TextField();
        TextField medecF  = new TextField();

        // Si modification, pré-remplissage des champs
        if (existingPatient != null) {
            nomF.setText(existingPatient.getNom());
            prenomF.setText(existingPatient.getPrenom());
            datePicker.setValue(existingPatient.getDateNaissance());
            cnF.setText(existingPatient.getNumCarteNationale());
            doseF.setText(existingPatient.getDoseMedicament());
            medsF.setText(existingPatient.getMedicaments());
            medecF.setText(existingPatient.getMedecinsSuivi());
        }

        // Ajout des champs au formulaire
        grid.addRow(0, new Label("Nom:"), nomF);
        grid.addRow(1, new Label("Prénom:"), prenomF);
        grid.addRow(2, new Label("Date de naissance:"), datePicker);
        grid.addRow(3, new Label("N° carte nat. :"), cnF);
        grid.addRow(4, new Label("Dose méd. :"), doseF);
        grid.addRow(5, new Label("Médicaments:"), medsF);
        grid.addRow(6, new Label("Médecins :"), medecF);

        dialog.getDialogPane().setContent(grid);

        // Désactivation du bouton tant que le champ nom est vide
        Node actionBtn = dialog.getDialogPane().lookupButton(actionType);
        actionBtn.setDisable(true);
        nomF.textProperty().addListener((o, ov, nv) -> actionBtn.setDisable(nv.trim().isEmpty()));

        // Si on modifie et que nom est déjà rempli, activer le bouton
        if (existingPatient != null && !nomF.getText().trim().isEmpty()) {
            actionBtn.setDisable(false);
        }

        // Définir le résultat à retourner quand l'utilisateur clique sur OK
        dialog.setResultConverter(bt -> {
            if (bt == actionType) {
                return new Patient(
                        existingPatient != null ? existingPatient.getId() : 0,
                        nomF.getText(),
                        prenomF.getText(),
                        datePicker.getValue(),
                        cnF.getText(),
                        doseF.getText(),
                        medsF.getText(),
                        medecF.getText()
                );
            }
            return null;
        });

        return dialog;
    }
}
