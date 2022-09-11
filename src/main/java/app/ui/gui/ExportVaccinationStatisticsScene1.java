package app.ui.gui;

import app.controller.ExportVaccinationStatisticsController;
import app.domain.model.VaccineAdministration;
import app.ui.MainFx;
import app.ui.gui.controller.CoordinatorGUI;
import app.ui.gui.controller.ExportVaccinationStatisticsGUI;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportVaccinationStatisticsScene1 implements Initializable
{
    private ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI;
    private CoordinatorGUI coordinatorGUI;


    private MainFx mainApp;

    @FXML
    private Label lblAlert;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private MFXTextField StartDate;

    @FXML
    private MFXTextField EndDate;

    @FXML
    private TextField txtNameOfFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
//        ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI = new ExportVaccinationStatisticsGUI(mainApp);
//        ExportVaccinationStatisticsController exportVaccinationStatisticsController = new ExportVaccinationStatisticsController();

    }

    public TextField getStarDate()
    {
        return StartDate;
    }

    public TextField getEndDate()
    {
        return EndDate;
    }

    public TextField getTxtNameOfFile()
    {
        return txtNameOfFile;
    }

    @FXML
    private void btnNextActioin(ActionEvent event)
    {
    }

    @FXML
    private void txtStartDatePressed(KeyEvent event)
    {
        this.lblAlert.setText(null);
    }

    @FXML
    private void txtEndDatePressed(KeyEvent event)
    {
        this.lblAlert.setText(null);
    }

    @FXML
    private void btnCancelAction(ActionEvent event)
    {
/*        ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI = new ExportVaccinationStatisticsGUI(mainApp);
        exportVaccinationStatisticsGUI.getMainApp().toLogin();*/
        coordinatorGUI.toCoordinatorScene1();
    }

    public void setExportStatisticsGUI(ExportVaccinationStatisticsGUI exportStatisticsGUI)
    {
        this.exportVaccinationStatisticsGUI = exportStatisticsGUI;
    }

    @FXML
    private void btnNextAction(ActionEvent actionEvent)
    {
        CoordinatorGUI coordinatorGUI = this.coordinatorGUI;
        ExportVaccinationStatisticsController exportVaccinationStatisticsController = coordinatorGUI.getExportVaccinationStatisticsController();

//        ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI = this.exportVaccinationStatisticsGUI;
//        ExportVaccinationStatisticsController exportVaccinationStatisticsController = this.exportVaccinationStatisticsGUI.getExportVaccinationStatisticsController();
//        ExportVaccinationStatisticsGUI exportVaccinationStatisticsGUI = new ExportVaccinationStatisticsGUI(mainApp);
//        ExportVaccinationStatisticsController exportVaccinationStatisticsController = new ExportVaccinationStatisticsController();
//        CoordinatorGUI coordinatorGUI = new CoordinatorGUI(mainApp);
        String startDateString = this.StartDate.getText();
        Date startDate = convertStringToDate(startDateString);
        Date endDate = convertStringToDate(EndDate.getText());

        if (startDate == null || endDate == null)
        {
            exportVaccinationStatisticsGUI.toExportVaccinationStatisticsScene2();
        }

        String nameOfFile = this.txtNameOfFile.getText();

        Set<VaccineAdministration> listOfFullyVaccinatedUsersByDate =
                exportVaccinationStatisticsController.getListOfFullyVaccinatedUsersByDate(startDate, endDate);

        exportVaccinationStatisticsController.printListOfUsers(listOfFullyVaccinatedUsersByDate, nameOfFile);

        coordinatorGUI.toCoordinatorScene1();
    }

    @FXML
    private void txtNameOfFilePressed(KeyEvent event)
    {
//        this.lblAlert.setText(null);
    }

    static private Date convertStringToDate(String stringDate)
    {
        try
        {
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dff.parse(stringDate);
            dff.format(date);
            return date;
        } catch (ParseException ex)
        {
            System.out.println("Date format invalid!");
            return null;
        }

    }

    public void setCoordinatorGUI(CoordinatorGUI coordinatorGUI)
    {
        this.coordinatorGUI = coordinatorGUI;
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }
}