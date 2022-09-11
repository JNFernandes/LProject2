package app.ui.gui;

import app.ui.MainFx;
import app.ui.gui.controller.LoginWarningGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWarningScene1 implements Initializable
{

    private MainFx mainApp;
    private LoginWarningGUI loginWarningGUI;

    @FXML
    private Button btnNonExistingUser;

    @FXML
    private Label lblNotification;

    @FXML
    void btnNonExistingUser(ActionEvent event)
    {
        this.loginWarningGUI.getMainApp().toLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public void setLoginWarningGUI(LoginWarningGUI loginWarningGUI)
    {
        this.loginWarningGUI = loginWarningGUI;
    }
}
