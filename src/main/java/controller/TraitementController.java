package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import module.Traitement;
import module.TraitementDAO;
import module.Patient;
import module.PatientDAO;
import module.Medecin;
import module.MedecinDAO;

import java.util.List;
import java.util.Optional;

public class TraitementController {

    @FXML private TableView<Traitement> traitementTable;
    @FXML private TableColumn<Traitement, Integer> idColumn;
    @FXML private TableColumn<Traitement, Integer> patientIdColumn;
    @FXML private TableColumn<Traitement, Integer> medecinIdColumn;
    @FXML private TableColumn<Traitement, String> patientNomColumn;
    @FXML private TableColumn<Traitement, String> medecinNomColumn;
    @FXML private TableColumn<Traitement, String> medicamentColumn;
    @FXML private TableColumn<Traitement, String> doseColumn;
    @FXML private TableColumn<Traitement, String> dateDebutColumn;
    @FXML private TableColumn<Traitement, String> dateFinColumn;
    @FXML private TableColumn<Traitement, Void> actionColumn;

    @FXML private Button addButton;
    @FXML private TextField searchField;

    private TraitementDAO traitementDAO;
    private PatientDAO patientDAO;
    private MedecinDAO medecinDAO;

    private ObservableList<Traitement> traitementList;
    private ObservableList<Patient> patients;
    private ObservableList<Medecin> medecins;

    @FXML
    public void initialize() {
        traitementDAO = new TraitementDAO();
        patientDAO = new PatientDAO();
        medecinDAO = new MedecinDAO();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        medecinIdColumn.setCellValueFactory(new PropertyValueFactory<>("medecinId"));
        patientNomColumn.setCellValueFactory(new PropertyValueFactory<>("patientNom"));
        medecinNomColumn.setCellValueFactory(new PropertyValueFactory<>("medecinNom"));
        medicamentColumn.setCellValueFactory(new PropertyValueFactory<>("medicament"));
        doseColumn.setCellValueFactory(new PropertyValueFactory<>("dose"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        patients = FXCollections.observableArrayList(patientDAO.getAllPatients());
        medecins = FXCollections.observableArrayList(medecinDAO.getAllMedecins());

        loadTraitements();

        addButton.setOnAction(e -> showAddDialog());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTraitements(newVal));
        addButtonToTable();
    }

    private void loadTraitements() {
        List<Traitement> list = traitementDAO.getAllTraitements();
        traitementList = FXCollections.observableArrayList(list);
        traitementTable.setItems(traitementList);
    }

    private void filterTraitements(String filter) {
        if (filter == null || filter.isEmpty()) {
            traitementTable.setItems(traitementList);
        } else {
            ObservableList<Traitement> filtered = FXCollections.observableArrayList();
            for (Traitement t : traitementList) {
                if (t.getMedicament().toLowerCase().contains(filter.toLowerCase()) ||
                        t.getPatientNom().toLowerCase().contains(filter.toLowerCase()) ||
                        t.getMedecinNom().toLowerCase().contains(filter.toLowerCase())) {
                    filtered.add(t);
                }
            }
            traitementTable.setItems(filtered);
        }
    }

    private void addButtonToTable() {
        Callback<TableColumn<Traitement, Void>, TableCell<Traitement, Void>> cellFactory = param -> new TableCell<>() {
            final Button btnEdit = new Button("Modifier");
            final Button btnDelete = new Button("Supprimer");

            {
                btnEdit.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                btnEdit.setOnAction(event -> {
                    Traitement t = getTableView().getItems().get(getIndex());
                    showEditDialog(t);
                });

                btnDelete.setOnAction(event -> {
                    Traitement t = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce traitement ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (traitementDAO.deleteTraitement(t.getId())) {
                            loadTraitements();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression.").showAndWait();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new VBox(5, btnEdit, btnDelete));
                }
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void showAddDialog() {
        showFormDialog(null);
    }

    private void showEditDialog(Traitement traitement) {
        showFormDialog(traitement);
    }

    private void showFormDialog(Traitement traitement) {
        boolean isEdit = traitement != null;

        Dialog<Traitement> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Modifier Traitement" : "Ajouter un Traitement");
        dialog.initModality(Modality.APPLICATION_MODAL);
        ButtonType saveButtonType = new ButtonType(isEdit ? "Sauvegarder" : "Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        ComboBox<Patient> cbPatient = new ComboBox<>(patients);
        ComboBox<Medecin> cbMedecin = new ComboBox<>(medecins);
        TextField tfMedicament = new TextField();
        TextField tfDose = new TextField();
        DatePicker dpDateDebut = new DatePicker();
        DatePicker dpDateFin = new DatePicker();

        cbPatient.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });
        cbPatient.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });

        cbMedecin.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Medecin item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });
        cbMedecin.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Medecin item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getPrenom() + " " + item.getNom());
            }
        });

        if (isEdit) {
            cbPatient.setValue(patients.stream().filter(p -> p.getId() == traitement.getPatientId()).findFirst().orElse(null));
            cbMedecin.setValue(medecins.stream().filter(m -> m.getId() == traitement.getMedecinId()).findFirst().orElse(null));
            tfMedicament.setText(traitement.getMedicament());
            tfDose.setText(traitement.getDose());
            if (!traitement.getDateDebut().isEmpty())
                dpDateDebut.setValue(java.time.LocalDate.parse(traitement.getDateDebut()));
            if (traitement.getDateFin() != null && !traitement.getDateFin().isEmpty())
                dpDateFin.setValue(java.time.LocalDate.parse(traitement.getDateFin()));
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Patient:"), cbPatient);
        grid.addRow(1, new Label("Médecin:"), cbMedecin);
        grid.addRow(2, new Label("Médicament:"), tfMedicament);
        grid.addRow(3, new Label("Dose:"), tfDose);
        grid.addRow(4, new Label("Date début:"), dpDateDebut);
        grid.addRow(5, new Label("Date fin:"), dpDateFin);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (cbPatient.getValue() == null || cbMedecin.getValue() == null ||
                        tfMedicament.getText().isEmpty() || tfDose.getText().isEmpty() ||
                        dpDateDebut.getValue() == null) {
                    new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs requis.").showAndWait();
                    return null;
                }
                Patient p = cbPatient.getValue();
                Medecin m = cbMedecin.getValue();
                return new Traitement(
                        isEdit ? traitement.getId() : 0,
                        p.getId(),
                        m.getId(),
                        p.getNom(),
                        m.getNom(),
                        tfMedicament.getText(),
                        tfDose.getText(),
                        dpDateDebut.getValue().toString(),
                        dpDateFin.getValue() != null ? dpDateFin.getValue().toString() : ""
                );
            }
            return null;
        });

        Optional<Traitement> result = dialog.showAndWait();
        result.ifPresent(t -> {
            boolean success = isEdit ? traitementDAO.updateTraitement(t) : traitementDAO.addTraitement(t);
            if (success) {
                loadTraitements();
            } else {
                new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement du traitement.").showAndWait();
            }
        });
    }
}
