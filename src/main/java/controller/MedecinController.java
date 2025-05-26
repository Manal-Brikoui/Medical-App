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

    @FXML private TableView<Medecin> medecinTable;
    @FXML private TableColumn<Medecin, Integer> idColumn;
    @FXML private TableColumn<Medecin, String> nomColumn;
    @FXML private TableColumn<Medecin, String> prenomColumn;
    @FXML private TableColumn<Medecin, String> specialiteColumn;
    @FXML private TableColumn<Medecin, Void> actionColumn;

    @FXML private TextField searchField;
    @FXML private Button addButton;

    private ObservableList<Medecin> medecinList;
    private FilteredList<Medecin> filteredData;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        loadMedecins();
        setupSearchFilter();
        addButtonToTable();

        addButton.setOnAction(e -> openAddMedecinDialog());
    }

    private void loadMedecins() {
        MedecinDAO dao = new MedecinDAO();
        medecinList = FXCollections.observableArrayList(dao.getAllMedecins());
    }

    private void setupSearchFilter() {
        filteredData = new FilteredList<>(medecinList, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(m -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String ft = newVal.toLowerCase();
                return m.getNom().toLowerCase().contains(ft)
                        || m.getPrenom().toLowerCase().contains(ft)
                        || (m.getSpecialite() != null && m.getSpecialite().toLowerCase().contains(ft));
            });
        });
        SortedList<Medecin> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(medecinTable.comparatorProperty());
        medecinTable.setItems(sortedData);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Medecin, Void>, TableCell<Medecin, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final VBox buttonBox = new VBox(5, btnEdit, btnDelete);

            {
                btnEdit.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                btnEdit.setOnAction(ev -> {
                    Medecin m = getTableView().getItems().get(getIndex());
                    openEditMedecinDialog(m);
                });

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

    private void openAddMedecinDialog() {
        Dialog<Medecin> dialog = createMedecinDialog(null);
        Optional<Medecin> res = dialog.showAndWait();
        res.ifPresent(m -> {
            new MedecinDAO().insertMedecin(m);
            medecinList.add(m);
            searchField.clear();
            filteredData.setPredicate(med -> true);
            medecinTable.refresh();
        });
    }

    private void openEditMedecinDialog(Medecin medecin) {
        Dialog<Medecin> dialog = createMedecinDialog(medecin);
        Optional<Medecin> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            medecin.setNom(updated.getNom());
            medecin.setPrenom(updated.getPrenom());
            medecin.setSpecialite(updated.getSpecialite());

            new MedecinDAO().updateMedecin(medecin);
            medecinTable.refresh();
        });
    }

    private Dialog<Medecin> createMedecinDialog(Medecin existingMedecin) {
        Dialog<Medecin> dialog = new Dialog<>();
        dialog.setTitle(existingMedecin == null ? "Ajouter un Médecin" : "Modifier un Médecin");
        ButtonType actionType = new ButtonType(existingMedecin == null ? "Ajouter" : "Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomF = new TextField();
        TextField prenomF = new TextField();
        TextField specialiteF = new TextField();

        if (existingMedecin != null) {
            nomF.setText(existingMedecin.getNom());
            prenomF.setText(existingMedecin.getPrenom());
            specialiteF.setText(existingMedecin.getSpecialite());
        }

        grid.addRow(0, new Label("Nom:"), nomF);
        grid.addRow(1, new Label("Prénom:"), prenomF);
        grid.addRow(2, new Label("Spécialité:"), specialiteF);

        dialog.getDialogPane().setContent(grid);

        Node actionBtn = dialog.getDialogPane().lookupButton(actionType);
        // Initialisation de l'état du bouton selon le contenu actuel de nomF
        actionBtn.setDisable(nomF.getText().trim().isEmpty());

        nomF.textProperty().addListener((o, ov, nv) -> actionBtn.setDisable(nv.trim().isEmpty()));

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