package app.ui.gui.controller;

import app.controller.ExportVaccinationStatisticsController;
import app.ui.MainFx;
import app.ui.gui.ExportVaccinationStatisticsScene1;
import app.ui.gui.ExportVaccinationStatisticsScene2;
import app.ui.gui.ExportVaccinationStatisticsScene3;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportVaccinationStatisticsGUI
{
    /**
     * The main app
     */
    private MainFx mainApp;

    /**
     * The controller for the exporter
     */
    private ExportVaccinationStatisticsController exportVaccinationStatisticsController;

    public ExportVaccinationStatisticsGUI(MainFx mainApp)
    {
        this.mainApp = mainApp;
        this.exportVaccinationStatisticsController = new ExportVaccinationStatisticsController();
    }

    public MainFx getMainApp()
    {
        return mainApp;
    }

    public ExportVaccinationStatisticsController getExportVaccinationStatisticsController()
    {
        return exportVaccinationStatisticsController;
    }

    /**
     * Method to get to Scene1.
     */
    public void toExportVaccinationStatisticsScene1()
    {
        try
        {
            ExportVaccinationStatisticsScene1 exportVaccinationStatisticsScene1 =
                    (ExportVaccinationStatisticsScene1) this.mainApp.replaceSceneContent("/fxml/ExportVaccinationStatisticsScene1.fxml",0,0);
            exportVaccinationStatisticsScene1.setMainApp(this.mainApp);
            exportVaccinationStatisticsScene1.setExportStatisticsGUI(this);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to get o Scene2.
     */
    public void toExportVaccinationStatisticsScene2()
    {
        try
        {
            ExportVaccinationStatisticsScene2 exportVaccinationStatisticsScene2 =
                    (ExportVaccinationStatisticsScene2) this.mainApp.replaceSceneContent("/fxml/ExportVaccinationStatisticsScene2.fxml",0,0);
            exportVaccinationStatisticsScene2.setMainApp(this.mainApp);
            exportVaccinationStatisticsScene2.setExportStatisticsGUI(this);

        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void toExportVaccinationStatisticsScene3()
    {
        try
        {
            ExportVaccinationStatisticsScene3 exportVaccinationStatisticsScene3 =
                    (ExportVaccinationStatisticsScene3) this.mainApp.replaceSceneContent("/fxml/ExportVaccinationStatisticsScene3.fxml", 0, 0);
            exportVaccinationStatisticsScene3.setMainApp(this.mainApp);
            exportVaccinationStatisticsScene3.setExportStatisticsGUI(this);

        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }


    }
}
