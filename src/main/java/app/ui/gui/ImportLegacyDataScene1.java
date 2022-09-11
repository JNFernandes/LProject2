package app.ui.gui;

import app.controller.ImportLegacyDataController;
import app.mappers.dto.LegacyDataDTO;
import app.ui.MainFx;
import app.ui.gui.controller.CoordinatorGUI;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.LongFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ImportLegacyDataScene1 implements Initializable {

    @FXML
    private CoordinatorGUI coordinatorGUI;

    @FXML
    private MainFx mainApp;

    @FXML
    private MFXComboBox<Comparator<LegacyDataDTO>> nCombo;

    @FXML
    private MFXTableView<LegacyDataDTO> tableView;

    private Comparator<LegacyDataDTO> chosenCriterion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCoordinatorGUI(CoordinatorGUI coordinatorGUI) {
        this.coordinatorGUI = coordinatorGUI;
    }

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }

    public void updateTableViewLegacyData() {

        ImportLegacyDataController controller = coordinatorGUI.getImportLegacyDataController();
        List<LegacyDataDTO> sortedList = controller.getSortedLegacyDataDTOList(chosenCriterion);
        ObservableList<LegacyDataDTO> observableList = FXCollections.observableList(sortedList);

        tableView.setItems(observableList);

    }

    public void setupSceneElements()
    {
        setupTableView();
        tableView.autosizeColumnsOnInitialization();
        ImportLegacyDataController controller = coordinatorGUI.getImportLegacyDataController();
        chosenCriterion = controller.getSortingCriteriaList().get(0);
        setupComboBox();
    }

    private void setupTableView() {
        MFXTableColumn<LegacyDataDTO> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(LegacyDataDTO::getSnsUserName));
        MFXTableColumn<LegacyDataDTO> snsColumn = new MFXTableColumn<>("Sns Number", true, Comparator.comparing(LegacyDataDTO::getSnsNumber));
        MFXTableColumn<LegacyDataDTO> vaccineTypeColumn = new MFXTableColumn<>("Vaccine Type", true, Comparator.comparing(LegacyDataDTO::getVaccineTypeDescription));
        MFXTableColumn<LegacyDataDTO> arrivalDateColumn = new MFXTableColumn<>("Arrival Date", true, Comparator.comparing(LegacyDataDTO::getArrivalDateTime));
        MFXTableColumn<LegacyDataDTO> administrationDateColumn = new MFXTableColumn<>("Administration Date", true, Comparator.comparing(LegacyDataDTO::getNurseAdministrationDateTime));
        MFXTableColumn<LegacyDataDTO> leavingDateColumn = new MFXTableColumn<>("Leaving Date", true, Comparator.comparing(LegacyDataDTO::getLeavingDateTime));

        nameColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getSnsUserName));
        snsColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getSnsNumber));
        vaccineTypeColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getVaccineTypeDescription));
        arrivalDateColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getArrivalDateTime));
        administrationDateColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getNurseAdministrationDateTime));
        leavingDateColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(LegacyDataDTO::getLeavingDateTime));

        tableView.getTableColumns().addAll(nameColumn, snsColumn, vaccineTypeColumn, arrivalDateColumn, administrationDateColumn, leavingDateColumn);
        tableView.getFilters().addAll(
                new StringFilter<>("Name", LegacyDataDTO::getSnsUserName),
                new LongFilter<>("Sns Number", LegacyDataDTO::getSnsNumber),
                new StringFilter<>("Vaccine Type", LegacyDataDTO::getVaccineTypeDescription)
        );
    }

    private void setupComboBox(){
        ImportLegacyDataController controller = coordinatorGUI.getImportLegacyDataController();
        List<Comparator<LegacyDataDTO>> sortCriteria = controller.getSortingCriteriaList();
        ObservableList<Comparator<LegacyDataDTO>> obsList = FXCollections.observableList(sortCriteria);
        nCombo.setItems(obsList);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                chosenCriterion = nCombo.getSelectedItem();
                updateTableViewLegacyData();
            }
        };

        nCombo.setOnAction(event);
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        coordinatorGUI.toCoordinatorScene1();
    }

}
