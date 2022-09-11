package app.ui.gui;

import app.ui.MainFx;
import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdministrationGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordAdministrationScene6 implements Initializable
{
    /**
     * Controller for Record Adiministration UI.
     */
    private RecordAdministrationGUI recordAdministrationGUI;
    private MainFx mainApp;

    @FXML
    private Button btnOK;

    @FXML
    private Label lblNotification;

    @FXML
    void btnOKAction(ActionEvent event)
    {
        this.mainApp = this.recordAdministrationGUI.getMainApp();
        NurseGUI nurseGUI = new NurseGUI(mainApp);
        nurseGUI.toNurseScene1();
    }

    public void setRecordAdministrationGUI(RecordAdministrationGUI recordAdministrationGUI)
    {
        this.recordAdministrationGUI = recordAdministrationGUI;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void showVaccineInformation()
    {
        boolean isUserFullyVaccinated = this.recordAdministrationGUI.getIsFullyVaccinated();
        String textToDisplay;

        if (isUserFullyVaccinated)
        {
            textToDisplay = "Sucess! User is fully vaccinated.";
        }else
        {
            textToDisplay = "Sucess! To the next dose!";
        }

        this.lblNotification.setText(textToDisplay);
    }
}
