package app.ui.gui.controller;

import app.ui.MainFx;
import app.ui.gui.LoginWarningScene1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginWarningGUI

{
    private MainFx mainApp;


    public LoginWarningGUI(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public void toLoginWarning1()
    {
        try
        {
            LoginWarningScene1 loginWarning1 = (LoginWarningScene1) this.mainApp.replaceSceneContent("/fxml/LoginWarningScene1.fxml",0,0);
            loginWarning1.setMainApp(this.mainApp);
            loginWarning1.setLoginWarningGUI(this);

        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

    public MainFx getMainApp()
    {
        return mainApp;
    }
}
