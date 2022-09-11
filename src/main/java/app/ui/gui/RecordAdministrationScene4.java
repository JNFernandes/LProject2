package app.ui.gui;

import app.domain.model.VaccineAdministration;
import app.mappers.dto.AdverseReactionDTO;
import app.mappers.dto.SnsUserAdverseReactionsDTO;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccineDTO;
import app.ui.gui.controller.RecordAdministrationGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.*;

public class RecordAdministrationScene4 implements Initializable
{
    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private Label lblAlert;

    @FXML
    private Label txtDosage;

    @FXML
    private Label txtDoseNumber;

    @FXML
    private TextField txtLoteNumber;

    @FXML
    private TextField txtAdverseReactions;

    @FXML
    void btnOKAction(ActionEvent event)
    {

    }

    @FXML
    void btnCancelAction(ActionEvent event)
    {
        this.recordAdministrationGUI.toRecordAdministrationScene3();
    }

    @FXML
    void btnNextAction(ActionEvent event)
    {
        boolean isNull;
        if (this.txtLoteNumber.getText().equals(""))
        {
            this.lblAlert.setText("Please select a Lote Number");
            this.txtLoteNumber.requestFocus();
        } else
        {
            this.recordAdministrationGUI.setLoteNumber(txtLoteNumber.getText());
            this.recordAdministrationGUI.toRecordAdministrationScene5();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void setRecordAdministrationGUI(RecordAdministrationGUI recordAdministrationGUI)
    {
        this.recordAdministrationGUI = recordAdministrationGUI;
    }

    public void showVaccineInformation(VaccineDTO chosenVaccine, SnsUserArrivalDTO snsUserArrivalDTO)
    {
        int doseNumber = 0;

        Set<VaccineAdministration> vaccineAdministrationStore = this.recordAdministrationGUI
                .getVaccineAdministrationController().getVaccineAdministrationStore().getVaccineAdministrationStore();

        Iterator<VaccineAdministration> iteratorVaccineAdministration = vaccineAdministrationStore.iterator();
        VaccineAdministration vaccineAdministration;

        while (iteratorVaccineAdministration.hasNext())
        {
            vaccineAdministration = iteratorVaccineAdministration.next();
            if (vaccineAdministration.getSnsUser().getSnsNumber().getNumber() == this.recordAdministrationGUI.getSnsUser().getSnsNumber()
                    && vaccineAdministration.getSnsUserArrival().getVaccineSchedule().getVaccineType().getDescription().equals(snsUserArrivalDTO.getVaccineSchedule().getVaccineType().getDescription()))
            {
                doseNumber++;
            }
        }

        doseNumber++;

        this.recordAdministrationGUI.setDoseNumber(doseNumber);

        double dosage = this.recordAdministrationGUI.getVaccineAdministrationController()
                .getDosageBasedOnVaccineAndDoseNumber
                        (
                                chosenVaccine,
                                snsUserArrivalDTO.getVaccineSchedule().getSnsUser().getAge(),
                                doseNumber
                        );


        this.txtDoseNumber.setText(String.valueOf("Dose number: " + doseNumber));
        this.txtDosage.setText(String.valueOf("Dosage: " + dosage + " ml"));
    }

    public void txtAdverseReactionPressed(KeyEvent keyEvent)
    {
        this.lblAlert.setText(null);
    }

    public void txtLoteNumberPressed(KeyEvent keyEvent)
    {
        this.lblAlert.setText(null);
    }

    public void showAdverseReactions(SnsUserArrivalDTO snsUser)
    {
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.recordAdministrationGUI.getVaccineAdministrationController()
                .getAllAdverseReactionBySnsNumberAndVaccineType(snsUser.getSnsNumber(), snsUser.getVaccineSchedule().getVaccineType().getCode());

        if (snsUserAdverseReactionsDTO.isEmpty())
        {
            this.txtAdverseReactions.setText("No adverse reactions Registered");
        } else
        {
            if (snsUserAdverseReactionsDTO.isPresent()
                    && !snsUserAdverseReactionsDTO.get().getAdverseReactions().isEmpty()) {
                StringJoiner joiner = new StringJoiner(" | ");
                for (AdverseReactionDTO adverseReactionDTO : snsUserAdverseReactionsDTO.get().getAdverseReactions()) {
                    String s = adverseReactionDTO.getDate()
                            + ", " + adverseReactionDTO.getVaccine().getVaccineType().getDescription()
                            + ", " + adverseReactionDTO.getAdverseReaction();
                    joiner.add(s);
                }
                String adverseReactions = joiner.toString();

                this.txtAdverseReactions.setText(adverseReactions);
            }
        }
    }

    public void cmbAdverseReactions(ActionEvent event)
    {
    }
}
