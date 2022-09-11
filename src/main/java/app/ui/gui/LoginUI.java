package app.ui.gui;

import app.controller.App;
import app.controller.AuthController;
import app.domain.shared.Constants;
import app.ui.MainFx;
import app.ui.console.AdminUI;
import app.ui.gui.controller.AdminGUI;
import app.ui.gui.controller.CoordinatorGUI;
import app.ui.gui.controller.LoginWarningGUI;
import app.ui.gui.controller.NurseGUI;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginUI implements Initializable
{

    private App app;

    private MainFx mainApp;

    private AuthController authController;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtLogin;

    @FXML
    private MFXPasswordField txtPassword;


    @FXML
    public void btnLoginAction(ActionEvent event)
    {
        authController = new AuthController();

        boolean success = false;
        String userEmail = txtLogin.getText();
        String userPassword = txtPassword.getText();
        success = authController.doLogin(userEmail, userPassword);
        String rolesString = "";

        if (!success)
        {
            LoginWarningGUI loginWarningGUI = new LoginWarningGUI(this.mainApp);
            loginWarningGUI.toLoginWarning1();
        } else
        {
            List<UserRoleDTO> roles = this.authController.getUserRoles();
            rolesString = roles.get(0).getDescription();
        }

        switch (rolesString)
        {
            case Constants.ROLE_CENTER_COORDINATOR:
                CoordinatorGUI coordinatorGUI = new CoordinatorGUI(this.mainApp);
                coordinatorGUI.toCoordinatorScene1();
                break;

            case Constants.ROLE_NURSE:
                NurseGUI nurseGUI = new NurseGUI(this.mainApp);
                nurseGUI.toNurseScene1();
                break;

            case Constants.ROLE_ADMIN:
                AdminGUI adminGUI = new AdminGUI(this.mainApp);
                adminGUI.toAdminScene1();
                break;

            default:
                LoginWarningGUI loginWarningGUI = new LoginWarningGUI(this.mainApp);
                loginWarningGUI.toLoginWarning1();
        }
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
    }

    public App getApp()
    {
        return this.app;
    }

    public MainFx getMainApp()
    {
        return mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.app = App.getInstance();
    }

    public void txtIdKeyPressed(KeyEvent keyEvent)
    {
    }

    @FXML
    public void btnNonExistingUser(ActionEvent actionEvent)
    {
        this.mainApp.toLogin();
    }
}
