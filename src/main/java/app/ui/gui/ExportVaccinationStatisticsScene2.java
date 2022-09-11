package app.ui.gui;

import app.ui.MainFx;
import app.ui.gui.controller.ExportVaccinationStatisticsGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ExportVaccinationStatisticsScene2 implements Initializable
{

    private ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI;
    private MainFx mainApp;

    @FXML
    private Button btnOK;

    @FXML
    private Label lblNotification;

    @FXML
    void btnOKAction(ActionEvent event)
    {
        this.exportVaccinationStatisticsGUI.toExportVaccinationStatisticsScene1();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void setExportVaccinationStatisticsGUI(ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI)
    {
        this.exportVaccinationStatisticsGUI = exportVaccinationStatisticsGUI;
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp=mainApp;
    }

    public void setExportStatisticsGUI(ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI)
    {
        this.exportVaccinationStatisticsGUI = exportVaccinationStatisticsGUI;
    }


}
