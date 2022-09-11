package app.ui.gui;

import app.domain.model.*;
import app.domain.store.SnsUserArrivalStore;
import app.mappers.VaccineAdministrationMapper;
import app.mappers.VaccineMapper;
import app.mappers.dto.VaccineAdministrationDTO;

import app.ui.gui.controller.RecordAdministrationGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class RecordAdministrationScene5 implements Initializable
{

    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnReturn;

    public void btnConfirmAction(ActionEvent event)
    {
        LocalDateTime administrationDate = LocalDateTime.now();
        LocalDateTime leavingDate = administrationDate.plusMinutes(this.recordAdministrationGUI.getTIME_TO_SEND_MESSAGE());
        VaccineAdministrationDTO vaccineAdministrationDTO = new VaccineAdministrationDTO
                (this.recordAdministrationGUI.getSnsUser(),
                        this.recordAdministrationGUI.getSnsUser().getVaccinationSchedule().getSnsUser(),
                        this.recordAdministrationGUI.getVaccineDTO(),
                        this.recordAdministrationGUI.getDoseNumber(),
                        this.recordAdministrationGUI.getLoteNumber(),
                        administrationDate,
                        leavingDate
                );

        boolean vaccinationScheduleChangeOfState=this.recordAdministrationGUI.getVaccineAdministrationController()
                .changeVaccinationScheduleState(vaccineAdministrationDTO);

        Set<SnsUserArrival> snsUserArrivalStoreSet = this.recordAdministrationGUI
                .getRegisterSnsUserArrivalController().getSnsUserArrivalStore().getSnsUserArrivalStore();

        Iterator<SnsUserArrival> iteratorArrivalStore = snsUserArrivalStoreSet.iterator();
        SnsUserArrival snsUserArrival;

        while (iteratorArrivalStore.hasNext())
        {
            snsUserArrival = iteratorArrivalStore.next();
            if (snsUserArrival.getSnsUserNumber().getNumber() == this.recordAdministrationGUI.getSnsUser().getSnsNumber())
            {
                snsUserArrival.getVaccineSchedule().setState(VaccinationScheduleState.FINALIZED);
            }
        }

        Set<VaccinationSchedule> vaccinationScheduleStore = this.recordAdministrationGUI
                .getRegisterSnsUserArrivalController().getVaccinationScheduleStore().getVaccinationScheduleStore();

        Iterator<VaccinationSchedule> iteratorVaccinaSchedule = vaccinationScheduleStore.iterator();
        VaccinationSchedule vaccinationSchedulei;

        while (iteratorVaccinaSchedule.hasNext())
        {
            vaccinationSchedulei = iteratorVaccinaSchedule.next();
            if (vaccinationSchedulei.getSnsUser().getSnsNumber().getNumber() == this.recordAdministrationGUI.getSnsUser().getSnsNumber())
            {
                vaccinationSchedulei.setState(VaccinationScheduleState.FINALIZED);
                VaccineMapper vaccineMapper = new VaccineMapper();
                Vaccine vaccine = vaccineMapper.toModel(this.recordAdministrationGUI.getVaccineDTO());
                vaccinationSchedulei.setVaccine(vaccine);
            }
        }

        boolean fullyVaccinatedUser = this.recordAdministrationGUI.getVaccineAdministrationController()
                .changeStateOfAdministrationWhenUserIsFullyVaccinated(vaccineAdministrationDTO);

        this.recordAdministrationGUI.setFullyVaccinated(fullyVaccinatedUser);
        int doseNumber = this.recordAdministrationGUI.getDoseNumber();

        if (!this.recordAdministrationGUI.getIsFullyVaccinated())
        {
            this.recordAdministrationGUI.setDoseNumber(doseNumber++);
        }

//        boolean registeredSuccessfully = this.recordAdministrationGUI.getVaccineAdministrationController()
//                .registerVaccineAdministration(vaccineAdministrationDTO);

        VaccineAdministrationMapper vaccineAdministrationMapper = new VaccineAdministrationMapper();
        VaccineAdministration vaccineAdministration = vaccineAdministrationMapper.toModel(vaccineAdministrationDTO);

        if (this.recordAdministrationGUI.getIsFullyVaccinated())
        {
            vaccineAdministration.setVaccineAdministrationState(VaccineAdministrationState.FULLYVACCINATED);
        }

        this.recordAdministrationGUI.getVaccineAdministrationController().getVaccineAdministrationStore().addVaccineAdministration(vaccineAdministration);

        this.recordAdministrationGUI.toRecordAdministrationScene6();
    }

    public void btnCancelAction(ActionEvent event)
    {
        this.recordAdministrationGUI.toRecordAdministrationScene4();
    }

    public void btnReturnAction(ActionEvent event)
    {
        this.recordAdministrationGUI.toRecordAdministrationScene1();
    }

    public void setRecordAdministrationGUI(RecordAdministrationGUI recordAdministrationGUI)
    {
        this.recordAdministrationGUI = recordAdministrationGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
