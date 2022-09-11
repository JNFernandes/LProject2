package app.ui.gui;

import app.mappers.dto.VaccinationCenterDTO;
import app.ui.MainFx;
import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdministrationGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecordAdministrationScene1 implements Initializable
{
    /**
     * Main app
     */
    private MainFx mainApp;
    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;
    private NurseGUI nurseGUI;
    private ObservableList<VaccinationCenterDTO> options;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblAlert;

    @FXML
    private ComboBox<VaccinationCenterDTO> cmbVaccinationCenter;

    @FXML
    void btnCancelAction(ActionEvent event)
    {
        this.mainApp = this.recordAdministrationGUI.getMainApp();
        NurseGUI nurseGUI = new NurseGUI(mainApp);
        nurseGUI.toNurseScene1();
    }

    @FXML
    public void btnNextAction(ActionEvent event)
    {
        boolean isNull;
        try
        {
            isNull = this.options.size() == 0 || this.cmbVaccinationCenter.getValue().equals(null);
            VaccinationCenterDTO vaccinationCenterDTO = this.cmbVaccinationCenter.getValue();

            this.recordAdministrationGUI.setVaccinationCenter(vaccinationCenterDTO);
            this.recordAdministrationGUI.toRecordAdministrationScene2();

        } catch (Exception exception)
        {
            this.lblAlert.setText("Please select a vaccination center!");
            this.cmbVaccinationCenter.requestFocus();
        }
    }

    public void setRecordAdministrationGUI(RecordAdministrationGUI recordAdministrationGUI)
    {
        this.recordAdministrationGUI = recordAdministrationGUI;
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public MainFx getMainApp()
    {
        return mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    /**
     * Method to initialize the combo box.
     */
    public void initComboBoxVaccinationCenter()
    {
        List<VaccinationCenterDTO> vaccinationCenter = this.recordAdministrationGUI.getRegisterSnsUserArrivalController()
                .getAllVaccinationCenters();

        this.options = FXCollections.observableArrayList(vaccinationCenter);
//        ObservableList<VaccinationCenterDTO> options = FXCollections.observableArrayList(vaccinationCenter);

        this.cmbVaccinationCenter.setItems(this.options);
    }

    public void cmbVaccinationCenterAction(ActionEvent event)
    {
        this.lblAlert.setText(null);
    }

//    public void cmbVaccinationCenterAction(ActionEvent event)
//    {
//    }
}
