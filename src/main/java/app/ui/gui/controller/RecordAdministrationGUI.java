package app.ui.gui.controller;

import app.controller.RegisterSnsUserArrivalController;
import app.controller.VaccineAdministrationController;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.mappers.dto.VaccineAdministrationDTO;
import app.mappers.dto.VaccineDTO;
import app.ui.MainFx;
import app.ui.gui.*;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordAdministrationGUI
{

    private MainFx mainApp;
    private VaccineAdministrationController vaccineAdministrationController;
    private RegisterSnsUserArrivalController registerSnsUserArrivalController;
    private SnsUserArrivalDTO snsUser;
    private VaccinationCenterDTO vaccinationCenterDTO;
    private VaccineDTO vaccineDTO;
    private String loteNumber;
    private int doseNumber;
    private boolean fullyVaccinated;

    public void setFullyVaccinated(boolean fullyVaccinated)
    {
        this.fullyVaccinated = fullyVaccinated;
    }

    public boolean getIsFullyVaccinated()
    {
        return fullyVaccinated;
    }

    private final long TIME_TO_SEND_MESSAGE = 30;

    public long getTIME_TO_SEND_MESSAGE()
    {
        return TIME_TO_SEND_MESSAGE;
    }


    public void setVaccinationCenter(VaccinationCenterDTO vaccinationCenterDTO)
    {
        this.vaccinationCenterDTO = vaccinationCenterDTO;
    }

    public void setSnsUser(SnsUserArrivalDTO snsUser)
    {
        this.snsUser = snsUser;
    }

    public void setLoteNumber(String loteNumber)
    {
        this.loteNumber = loteNumber;
    }

    public RecordAdministrationGUI(MainFx mainApp)
    {
        this.mainApp = mainApp;
        this.vaccineAdministrationController = new VaccineAdministrationController();
        this.registerSnsUserArrivalController = new RegisterSnsUserArrivalController();
        this.registerSnsUserArrivalController = new RegisterSnsUserArrivalController();

    }

    public MainFx getMainApp()
    {
        return mainApp;
    }

    public VaccineAdministrationController getVaccineAdministrationController()
    {
        return vaccineAdministrationController;
    }

    public RegisterSnsUserArrivalController registerSnsUserArrivalController()
    {
        return registerSnsUserArrivalController;
    }




    public RegisterSnsUserArrivalController getRegisterSnsUserArrivalController()
    {
        return registerSnsUserArrivalController;
    }

    public void toRecordAdministrationScene1()
    {
        try
        {
            RecordAdministrationScene1 recordAdministrationScene1 =
                    (RecordAdministrationScene1) this.mainApp
                            .replaceSceneContent("/fxml/RecordAdministrationScene1.fxml", 0, 0);
            recordAdministrationScene1.setRecordAdministrationGUI(this);
            recordAdministrationScene1.initComboBoxVaccinationCenter();
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVaccineDTO(VaccineDTO vaccineDTO)
    {
        this.vaccineDTO = vaccineDTO;
    }

    public void toRecordAdministrationScene2()
    {
        try
        {
            RecordAdministrationScene2 recordAdministrationScene2
                    = (RecordAdministrationScene2) this.mainApp.
                    replaceSceneContent("/fxml/RecordAdministrationScene2.fxml", 0, 0);
            recordAdministrationScene2.setRecordAdministrationGUI(this);
            recordAdministrationScene2.initComboBoxUsersWaintingRoom(this.vaccinationCenterDTO);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toRecordAdministrationScene3()
    {
        try
        {
            RecordAdministrationScene3 recordAdministrationScene3 =
                    (RecordAdministrationScene3) this.mainApp.
                            replaceSceneContent("/fxml/RecordAdministrationScene3.fxml", 0, 0);
            recordAdministrationScene3.setRecordAdministrationGUI(this);
            recordAdministrationScene3.initComboBoxVaccine(this.snsUser);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toRecordAdministrationScene4()
    {
        try
        {
            RecordAdministrationScene4 recordAdministrationScene4 =
                    (RecordAdministrationScene4) this.mainApp.
                            replaceSceneContent("/fxml/RecordAdministrationScene4.fxml", 0, 0);
            recordAdministrationScene4.setRecordAdministrationGUI(this);
            recordAdministrationScene4.showVaccineInformation(this.vaccineDTO, this.snsUser);
            recordAdministrationScene4.showAdverseReactions(this.snsUser);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SnsUserArrivalDTO getSnsUser()
    {
        return snsUser;
    }

    public VaccinationCenterDTO getVaccinationCenterDTO()
    {
        return vaccinationCenterDTO;
    }

    public VaccineDTO getVaccineDTO()
    {
        return vaccineDTO;
    }

    public String getLoteNumber()
    {
        return loteNumber;
    }

    public int getDoseNumber()
    {
        return doseNumber;
    }

    public void setDoseNumber(int doseNumber)
    {
        this.doseNumber = doseNumber;
    }

    public void toRecordAdministrationScene5()
    {
        try
        {
            RecordAdministrationScene5 recordAdministrationScene5 =
                    (RecordAdministrationScene5) this.mainApp.
                            replaceSceneContent("/fxml/RecordAdministrationScene5.fxml", 0, 0);
            recordAdministrationScene5.setRecordAdministrationGUI(this);
//            LocalDateTime administrationDate = LocalDateTime.now();
//            LocalDateTime leavingDate = administrationDate.plusMinutes(30);
//            VaccineAdministrationDTO vaccineAdministrationDTO = new VaccineAdministrationDTO
//                    (this.snsUser,
//                            this.snsUser.getVaccinationSchedule().getSnsUser(),
//                            this.vaccineDTO, this.doseNumber, this.loteNumber, administrationDate, leavingDate
//                    );
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toRecordAdministrationScene6()
    {
        try
        {
            RecordAdministrationScene6 recordAdministrationScene6 =
                    (RecordAdministrationScene6) this.mainApp.
                            replaceSceneContent("/fxml/RecordAdministrationScene6.fxml", 0, 0);
            recordAdministrationScene6.setRecordAdministrationGUI(this);
            recordAdministrationScene6.showVaccineInformation();
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
