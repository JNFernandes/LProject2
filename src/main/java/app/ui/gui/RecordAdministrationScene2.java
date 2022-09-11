package app.ui.gui;

import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccinationCenterDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecordAdministrationScene2 implements Initializable
{
    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;
    private ObservableList<SnsUserArrivalDTO> options;


    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private ComboBox<SnsUserArrivalDTO> cmbUsersWaitingRoom;

    @FXML
    private Label lblAlert;

    @FXML
    void btnCancelAction(ActionEvent event)
    {
        this.recordAdministrationGUI.toRecordAdministrationScene1();
    }

    @FXML
    void btnNextAction(ActionEvent event)
    {
        boolean isNull;
        try
        {
            isNull = this.options.size() == 0 || this.cmbUsersWaitingRoom.getValue().equals(null);

            SnsUserArrivalDTO snsUserArrivalDTO = this.cmbUsersWaitingRoom.getValue();
            this.recordAdministrationGUI.setSnsUser(snsUserArrivalDTO);
            this.recordAdministrationGUI.toRecordAdministrationScene3();
        } catch (Exception exception)
        {
            this.lblAlert.setText("Please select a proper user!");
            this.cmbUsersWaitingRoom.requestFocus();
        }
    }

//    @FXML
//    void cmbUsersWaitingRoomAction(ActionEvent event)
//    {
//
//    }

    public void setRecordAdministrationGUI(RecordAdministrationGUI recordAdministrationGUI)
    {
        this.recordAdministrationGUI = recordAdministrationGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    /**
     * Method to initialize the combo box.
     * @param vaccinationCenterDTO object to be passed
     */
    public void initComboBoxUsersWaintingRoom(VaccinationCenterDTO vaccinationCenterDTO)
    {
        List<SnsUserArrivalDTO> snsUsersWainting = this.recordAdministrationGUI
                .getVaccineAdministrationController()
                .getSnsUsersOnWaitingRoom(vaccinationCenterDTO.getVaccinationCenterEmailAddress());

        this.options = FXCollections.observableArrayList(snsUsersWainting);

        this.cmbUsersWaitingRoom.setItems(options);
    }

    public void cmbUsersWaitingRoomAction(ActionEvent event)
    {
        this.lblAlert.setText(null);
    }
}
