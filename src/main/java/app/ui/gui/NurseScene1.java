package app.ui.gui;

import app.controller.App;
import app.controller.AuthController;
import app.controller.ExportVaccinationStatisticsController;
import app.ui.MainFx;
import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdministrationGUI;
import app.ui.gui.controller.RecordAdverseReactionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class NurseScene1 implements Initializable
{

    private MainFx mainApp;
    private ExportVaccinationStatisticsController exportVaccinationStatisticsController;

    private NurseGUI nurseGUI;

    @FXML
    private Button btnRecordAdverseReaction;

    @FXML
    private Button btnOtherOption;

    @FXML
    private Button btnAnotherOption;

    @FXML
    private Button btnCancel;

    @FXML
    private void btnRecordAdverseReaction(ActionEvent event)
    {
        RecordAdverseReactionGUI recordAdverseReactionGUI = new RecordAdverseReactionGUI(this.mainApp, this.nurseGUI);
        recordAdverseReactionGUI.toRecordAdverseReactionScene1();
    }

    @FXML
    private void btnOtherOption(ActionEvent event)
    {

    }

    @FXML
    private void btnAnotherOption(ActionEvent event)
    {

    }

    @FXML
    private void btnCancelAction(ActionEvent event)
    {
        AuthController authController = new AuthController();
        authController.doLogout();
        this.nurseGUI.getMainApp().toLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        App.getInstance();
    }

    public void setNurseGUI(NurseGUI nurseGUI)
    {
        this.nurseGUI = nurseGUI;
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public void btnRecordAdministration(ActionEvent event)
    {
        RecordAdministrationGUI RecordAdministrationGUI = new RecordAdministrationGUI(this.mainApp);
        RecordAdministrationGUI.toRecordAdministrationScene1();
    }
}
