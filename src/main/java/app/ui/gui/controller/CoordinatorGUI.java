package app.ui.gui.controller;

import app.controller.App;
import app.controller.CenterPerformanceController;
import app.controller.ExportVaccinationStatisticsController;
import app.controller.ImportLegacyDataController;
import app.ui.MainFx;
import app.ui.gui.CenterPerformanceScene1;
import app.ui.gui.CoordinatorScene1;
import app.ui.gui.ExportVaccinationStatisticsScene1;
import app.ui.gui.LoginUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import app.ui.gui.*;
import app.ui.console.ChooseVaccinationCenterForCoordinatorUI;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorGUI {
    private App app;
    private MainFx mainApp;
    private ExportVaccinationStatisticsController exportVaccinationStatisticsController;
    private CenterPerformanceController centerPerformanceController;
    private CoordinatorGUI coordinatorGUI;
    private ImportLegacyDataController importLegacyDataController;
    private ChooseVaccinationCenterForCoordinatorUI chooseVaccinationCenterForCoordinatorUI;

//    private CoordinatorGUI coordinatorGUI;


    public CoordinatorGUI(MainFx mainApp)
    {
        this.mainApp = mainApp;
        this.app = App.getInstance();
        this.exportVaccinationStatisticsController = new ExportVaccinationStatisticsController();
        this.importLegacyDataController = new ImportLegacyDataController();
        this.chooseVaccinationCenterForCoordinatorUI = new ChooseVaccinationCenterForCoordinatorUI();
        this.centerPerformanceController = new CenterPerformanceController();
        mainApp.toLogin();
    }

    public void toCoordinatorScene1()
    {
        try
        {
            CoordinatorScene1 coordinatorScene1 = (CoordinatorScene1) this.mainApp.replaceSceneContent("/fxml/CoordinatorScene1.fxml", 0, 0);
            coordinatorScene1.setMainApp(this.mainApp);
            coordinatorScene1.setCoordinatorGUI(this);

        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toExportVaccinationStatisticsScene1()
    {
        try
        {
            ExportVaccinationStatisticsScene1 exportVaccinationStatisticsScene1 =
                    (ExportVaccinationStatisticsScene1) this.mainApp.replaceSceneContent("/fxml/ExportVaccinationStatisticsScene1.fxml", 0, 0);
            exportVaccinationStatisticsScene1.setMainApp(this.mainApp);
            exportVaccinationStatisticsScene1.setCoordinatorGUI(this);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainFx getMainApp()
    {
        return mainApp;
    }

    public App getApp() {
        return this.app;
    }

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }

    public ExportVaccinationStatisticsController getExportVaccinationStatisticsController()
    {
        return exportVaccinationStatisticsController;
    }

    public ImportLegacyDataController getImportLegacyDataController() {
        return importLegacyDataController;
    }

    public void toImportLegacyDataScene() {
        try {
            ImportLegacyDataScene1 importLegacyDataScene =
                    (ImportLegacyDataScene1) this.mainApp.replaceSceneContent("/fxml/ImportLegacyDataScene1.fxml", 800, 300);
            importLegacyDataScene.setMainApp(this.mainApp);
            importLegacyDataScene.setCoordinatorGUI(this);
            importLegacyDataScene.setupSceneElements();
            importLegacyDataScene.updateTableViewLegacyData();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

    public CenterPerformanceController getCenterPerformanceController() {
        return centerPerformanceController;
    }
    public void toCenterPerformanceScene1() {
        try {
            CenterPerformanceScene1 centerPerformanceScene1 =
                    (CenterPerformanceScene1) this.mainApp.replaceSceneContent("/fxml/AnalysePerformanceScene1.fxml", 0, 0);
            centerPerformanceScene1.setMainApp(this.mainApp);
            centerPerformanceScene1.setCoordinatorGUI(this);
            centerPerformanceScene1.setComboBox();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void toCenterPerformanceScene2(LocalDate day, Integer minutes, String email) {
        try {
            CenterPerformanceScene2 centerPerformanceScene2 =
                    (CenterPerformanceScene2) this.mainApp.replaceSceneContent("/fxml/AnalysePerformanceScene2.fxml", 1200,450);
            centerPerformanceScene2.setMainApp(this.mainApp);
            centerPerformanceScene2.setCoordinatorGUI(this);
            centerPerformanceScene2.showResult(day, minutes, email);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
