package app.ui.gui;

import app.controller.App;
import app.ui.MainFx;
import app.ui.gui.controller.CoordinatorGUI;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.mockito.cglib.core.Local;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CenterPerformanceScene1 implements Initializable {

    private MainFx mainApp;

    @FXML
    private CoordinatorGUI coordinatorGUI;

    @FXML
    private MFXComboBox<LocalDate> nCombo;

    @FXML
    private TextField txtMinutes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setCoordinatorGUI(CoordinatorGUI coordinatorGUI) {
        this.coordinatorGUI = coordinatorGUI;
    }

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    public void setComboBox() {

        String vaccinationCenterEmail = getVaccinationCenterEmail();
        List<LocalDate> optional = coordinatorGUI.getCenterPerformanceController().getListDaysForPerformance(vaccinationCenterEmail);
        nCombo.setItems(FXCollections.observableArrayList(optional));
    }

    private String getVaccinationCenterEmail()
    {
        App app = coordinatorGUI.getApp();
        String userEmail = app.getCurrentUserSession().getUserId().getEmail();
        return app.getCompany().getEmployeeStore().getEmployeeByEmail(userEmail).get().getVaccinationCenter().getVaccinationCenterEmailAddress().getEmail();
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        coordinatorGUI.toCoordinatorScene1();
    }

    public void btnConfirm(ActionEvent actionEvent) {
        LocalDate day = nCombo.getSelectedItem();
        String minutes = txtMinutes.getText();
        Integer number = 0 ;
        try{
            number = Integer.valueOf(minutes);
        }
        catch (NumberFormatException ex){
            System.out.println(ex.getMessage());
        }

        String email = getVaccinationCenterEmail();


        CoordinatorGUI coordinatorGUI = new CoordinatorGUI(mainApp);
        coordinatorGUI.getMainApp();
        coordinatorGUI.toCenterPerformanceScene2(day, number, email);
    }

}
