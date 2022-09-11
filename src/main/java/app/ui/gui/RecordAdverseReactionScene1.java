package app.ui.gui;

import app.mappers.dto.AdverseReactionDTO;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccineDTO;
import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdverseReactionGUI;
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
import jfxtras.scene.control.LocalDateTimeTextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Set;

public class RecordAdverseReactionScene1 implements Initializable {

    private NurseGUI nurseGUI;
    private RecordAdverseReactionGUI recordAdverseReactionGUI;

    @FXML
    private Label lblAlert;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtSnsNumber;

    @FXML
    private TextField txtAdverseReaction;

    @FXML
    private ComboBox<String> cmbVaccineDTO;

    @FXML
    private LocalDateTimeTextField dateTimePicker;

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle)
    {
    }

    public TextField getTxtSnsNumber()
    {
        return txtSnsNumber;
    }

    public void setNurseGUI(NurseGUI nurseGUI)
    {
        this.nurseGUI = nurseGUI;
    }

    public void setRecordAdverseReactionGUI(RecordAdverseReactionGUI recordAdverseReactionGUI)
    {
        this.recordAdverseReactionGUI = recordAdverseReactionGUI;
    }

    public void initComboBox()
    {
        Set<String> vaccinesNames = this.recordAdverseReactionGUI.getRecordAdverseReactionController()
                .getAllVaccinesNames();

        ObservableList<String> options = FXCollections.observableArrayList(vaccinesNames);

        this.cmbVaccineDTO.setItems(options);
    }

    public void showAdverseReaction()
    {
        this.txtSnsNumber.setText(String.valueOf(this.recordAdverseReactionGUI.getRecordAdverseReactionController().getAdverseReaction().getSnsUser().getSnsNumber().getNumber()));
        this.cmbVaccineDTO.setValue(this.recordAdverseReactionGUI.getRecordAdverseReactionController().getAdverseReaction().getVaccine().getVaccineName());
        this.txtAdverseReaction.setText(this.recordAdverseReactionGUI.getRecordAdverseReactionController().getAdverseReaction().getAdverseReaction());
        this.dateTimePicker.setLocalDateTime(this.recordAdverseReactionGUI.getRecordAdverseReactionController().getAdverseReaction().getDate());
    }

    @FXML
    private void btnNextAction(ActionEvent event)
    {
        try
        {
            String vaccine = this.cmbVaccineDTO.getSelectionModel().getSelectedItem();
            long snsUserNumber = Long.parseLong(this.txtSnsNumber.getText());
            String adverseReaction = this.txtAdverseReaction.getText();
            LocalDateTime date = this.dateTimePicker.getLocalDateTime();

            SnsUserDTO snsUserDTO = this.recordAdverseReactionGUI.getRecordAdverseReactionController()
                    .getSnsUserDTOBySnsNumber(snsUserNumber);

            VaccineDTO vaccineDTO = this.recordAdverseReactionGUI.getRecordAdverseReactionController()
                    .getVaccineDTO(vaccine);

            AdverseReactionDTO adverseReactionDTO = new AdverseReactionDTO(
                    snsUserDTO,
                    adverseReaction,
                    date,
                    vaccineDTO
            );

            this.recordAdverseReactionGUI.getRecordAdverseReactionController()
                    .createAdverseReaction(adverseReactionDTO);

            this.recordAdverseReactionGUI.toRecordAdverseReactionScene2();
        } catch (NumberFormatException ex)
        {
            this.lblAlert.setText("Invalid number!");
            this.txtSnsNumber.requestFocus();
        } catch (IllegalArgumentException ex)
        {
            this.lblAlert.setText(ex.getMessage());
            if (ex.getMessage().toLowerCase().contains("name"))
            {
                this.txtAdverseReaction.requestFocus();
            } else
            {
                this.cmbVaccineDTO.requestFocus();
            }
        }
    }

    @FXML
    private void txtAdverseReactionPressed(KeyEvent event)
    {
        this.lblAlert.setText(null);
    }

    @FXML
    private void txtSnsNumberPressed(KeyEvent event)
    {
        this.lblAlert.setText(null);
    }

    @FXML
    private void cmbVaccineDTOAction(ActionEvent event)
    {
        this.lblAlert.setText(null);
    }

    @FXML
    private void dateTimePickerAction(KeyEvent event)
    {
        this.dateTimePicker.setLocalDateTime(null);
    }

    @FXML
    private void btnCancelAction(ActionEvent event)
    {
        //AuthController authController = new AuthController();
        //authController.doLogout();
        //this.recordAdverseReactionGUI.getMainApp().toLogin();
        this.nurseGUI.toNurseScene1();
    }

}
