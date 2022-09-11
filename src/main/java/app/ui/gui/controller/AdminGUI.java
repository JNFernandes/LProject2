package app.ui.gui.controller;

import app.controller.*;
import app.ui.MainFx;
import app.ui.gui.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminGUI {
    private App app;
    private MainFx mainApp;
    private LoadCsvFileController loadCsvFileController;

    public AdminGUI(MainFx mainApp) {
        this.mainApp = mainApp;
        this.app = App.getInstance();
        this.loadCsvFileController = new LoadCsvFileController();
        mainApp.toLogin();
    }

    public void toAdminScene1() {
        try {
            AdminScene1 adminScene1 = (AdminScene1) this.mainApp.replaceSceneContent("/fxml/AdminScene1.fxml", 0, 0);
            adminScene1.setMainApp(this.mainApp);
            adminScene1.setAdminGUI(this);

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainFx getMainApp() {
        return mainApp;
    }

    public App getApp() {
        return this.app;
    }

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }

    public LoadCsvFileController getLoadCsvFileController()
    {
        return loadCsvFileController;
    }
}

