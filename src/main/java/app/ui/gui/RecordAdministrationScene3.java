package app.ui.gui;

import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccineDTO;
import app.ui.gui.controller.RecordAdministrationGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecordAdministrationScene3 implements Initializable
{
    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;
    private ObservableList<VaccineDTO> options;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private ComboBox<VaccineDTO> cmbVaccineDTO;

    @FXML
    private Label lblAlert;

    @FXML
    void btnCancelAction(ActionEvent event)
    {
        this.recordAdministrationGUI.toRecordAdministrationScene2();
    }

    @FXML
    void btnNextAction(ActionEvent event)
    {
        boolean isNull;
        try
        {
            isNull = this.options.size() == 0 || this.cmbVaccineDTO.getValue().equals(null);
            VaccineDTO vaccineDTO = this.cmbVaccineDTO.getValue();
            this.recordAdministrationGUI.setVaccineDTO(vaccineDTO);
            this.recordAdministrationGUI.toRecordAdministrationScene4();
        } catch (Exception ex)
        {
            this.lblAlert.setText("Please select a vaccine!");
            this.cmbVaccineDTO.requestFocus();
        }
    }

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
      * @param snsUserArrivalDTO object to be passed
     */
    public void initComboBoxVaccine(SnsUserArrivalDTO snsUserArrivalDTO)
    {
        List<VaccineDTO> vaccinesAvailable = this.recordAdministrationGUI.getVaccineAdministrationController()
                .getAllAvailableVaccinesByVaccineTypeAndAge(snsUserArrivalDTO.getVaccinationSchedule().getSnsUser().getAge(),
                        snsUserArrivalDTO.getVaccinationSchedule().getVaccineType());

        this.options = FXCollections.observableArrayList(vaccinesAvailable);

        this.cmbVaccineDTO.setItems(options);
    }

    public void cmbVaccineDTOAction(ActionEvent event)
    {
        this.lblAlert.setText(null);
    }
}
