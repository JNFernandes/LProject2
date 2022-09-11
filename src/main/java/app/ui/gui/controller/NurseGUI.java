package app.ui.gui.controller;

import app.ui.MainFx;
import app.ui.gui.NurseScene1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NurseGUI
{
    private MainFx mainApp;

    public NurseGUI(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public MainFx getMainApp()
    {
        return this.mainApp;
    }

    public void toNurseScene1()
    {
        try
        {
            NurseScene1 nurseScene1 = (NurseScene1) this.mainApp.replaceSceneContent("/fxml/NurseScene1.fxml", 0, 0);
            nurseScene1.setMainApp(this.mainApp);
            nurseScene1.setNurseGUI(this);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

}
