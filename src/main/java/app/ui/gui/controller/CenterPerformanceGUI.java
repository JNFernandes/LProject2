package app.ui.gui.controller;

import app.controller.CenterPerformanceController;

import app.ui.MainFx;
import app.ui.gui.CenterPerformanceScene1;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CenterPerformanceGUI {

    private MainFx mainApp;

    public CenterPerformanceController getCenterPerformanceController()
    {
        return centerPerformanceController;
    }

    private CenterPerformanceController centerPerformanceController;

    @FXML
    private Button buttoConfirm;

    @FXML
    private Button buttonBack;

    @FXML
    private ComboBox<LocalDate> comboBox;

    public CenterPerformanceGUI(MainFx mainAPP) {
        this.mainApp = mainAPP;
        this.centerPerformanceController = new CenterPerformanceController();
    }

//    public void toCenterPerformanceScene1() {
//        try {
//            CenterPerformanceScene1 centerPerformanceScene1 =
//                    (CenterPerformanceScene1) this.mainApp
//                            .replaceSceneContent("/fxml/CenterPerformanceScene1.fxml");
//            centerPerformanceScene1.setCenterPerformanceGUI(this);
//        } catch (Exception ex) {
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
