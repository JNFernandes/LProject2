package app.ui.gui;

import app.controller.App;
import app.controller.AuthController;
import app.controller.ImportLegacyDataController;
import app.externalmodule.HeaderNotMatchException;
import app.externalmodule.InvalidTypeFileException;
import app.mappers.dto.LegacyDataDTO;
import app.ui.MainFx;
import app.ui.gui.controller.CoordinatorGUI;
import app.ui.gui.controller.ExportVaccinationStatisticsGUI;
import app.ui.gui.controller.RecordAdverseReactionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorScene1 implements Initializable
{

    private MainFx mainApp;

    private App app;

    private CoordinatorGUI coordinatorGUI;

    private CoordinatorScene1 coordinatorScene1;

    private ExportVaccinationStatisticsScene1 exportVaccinationStatisticsScene1;

    private LoginUI loginUI;

    private AuthController authController;

    @FXML
    private Button btnAnalysePerformance;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnExportVaccinationStatistics;

    @FXML
    private Button btnImportData;

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle)
    {
    }

    @FXML
    private void btnExportVaccinationStatistics(ActionEvent event)
    {
        this.coordinatorGUI.toExportVaccinationStatisticsScene1();
    }

    public void setCoordinatorGUI(CoordinatorGUI coordinatorGUI)
    {
        this.coordinatorGUI = coordinatorGUI;
    }

    @FXML
    public void btnAnalysePerformance(ActionEvent actionEvent)
    {
        CoordinatorGUI coordinatorGUI = new CoordinatorGUI(this.mainApp);
        coordinatorGUI.toCenterPerformanceScene1();
    }

    @FXML
    public void btnImportData(ActionEvent actionEvent)
    {
        FileChooser fileChooser = FileChooserLegacyDataUI.createFileChooserLegacyData();
        File importFile = fileChooser.showOpenDialog(btnCancel.getScene().getWindow());

        if (importFile != null)
        {
            ImportLegacyDataController importLegacyDataController = coordinatorGUI.getImportLegacyDataController();
            String vcEmail = getVaccinationCenterEmail();
            try
            {
                boolean registeredWithSuccess = importLegacyDataController
                        .importLegacyDataFromExternalSource(importFile.getPath(), vcEmail);
                if (registeredWithSuccess)
                {
                    coordinatorGUI.toImportLegacyDataScene();
                }
                else
                {
                }

            }
            catch(FileNotFoundException e)
            {
                AlertUI.createAlert(
                        Alert.AlertType.ERROR, "Error", "File not found exception",
                        "The file you are trying to import can not be found."
                ).show();
            }
            catch(HeaderNotMatchException e)
            {
                AlertUI.createAlert(
                        Alert.AlertType.ERROR, "Error", "Invalid File Header",
                        "The header of the file you are trying to import is not correct."
                ).show();
            }
            catch(NoSuchElementException e)
            {
                AlertUI.createAlert(
                        Alert.AlertType.ERROR, "Error", "Runtime exception",
                        "An error occurred, unable to read the file."
                ).show();
            }

        }
        else
        {
            AlertUI.createAlert(
                    Alert.AlertType.WARNING, "Warning", "No file selected",
                    "Unable to import data because no file was selected."
            ).show();
        }

    }

    public void btnCancel(ActionEvent actionEvent)
    {
        AuthController authController = new AuthController();
        authController.doLogout();
        this.coordinatorGUI.getMainApp().toLogin();
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
        this.app = App.getInstance();
    }

    private String getVaccinationCenterEmail()
    {
        App app = coordinatorGUI.getApp();
        String userEmail = app.getCurrentUserSession().getUserId().getEmail();
        return app.getCompany().getEmployeeStore().getEmployeeByEmail(userEmail).get().getVaccinationCenter().getVaccinationCenterEmailAddress().getEmail();
    }
}
