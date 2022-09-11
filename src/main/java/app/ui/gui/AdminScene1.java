package app.ui.gui;


import app.controller.App;
import app.controller.AuthController;
import app.controller.LoadCsvFileController;
import app.mappers.dto.SnsUserDTO;
import app.ui.MainFx;
import app.ui.gui.controller.AdminGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminScene1 implements Initializable
{

    private MainFx mainApp;

    private App app;

    private AdminGUI adminGUI;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnImportSnsUsers;

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle)
    {
    }

    @FXML
    public void btnImportSnsUsers(ActionEvent actionEvent)
    {
        FileChooser fileChooser = FileChooserLegacyDataUI.createFileChooserLegacyData();
        File importFile = fileChooser.showOpenDialog(btnCancel.getScene().getWindow());
        LoadCsvFileController controller = adminGUI.getLoadCsvFileController();

        if (importFile != null)
        {
            try
            {
                List<SnsUserDTO> dtoList = controller.getListFromExternalModule(importFile.getPath());
                List<SnsUserDTO> finalSnsUserList = controller.registerSnsUsersFromExternalModule(dtoList);
                if (finalSnsUserList.size() == 0) {
                    AlertUI.createAlert(
                            Alert.AlertType.WARNING, "Info", "No users to import",
                            "The users in the selected file cannot be registered in the system."
                    ).show();
                }
                else
                {
                   int numberImportedUsers = finalSnsUserList.size();
                    AlertUI.createAlert(
                            Alert.AlertType.INFORMATION, "Success", "Users imported successfully",
                            String.format("%d users imported with success!%n", numberImportedUsers)
                    ).show();
                }

            }
            catch(FileNotFoundException e)
            {
                AlertUI.createAlert(
                        Alert.AlertType.ERROR, "Error", "File not found exception",
                        "The file you are trying to import can not be found."
                ).show();
            }

        }
        else
        {
            AlertUI.createAlert(
                    Alert.AlertType.WARNING, "Warning", "No file selected",
                    "Unable to import data because no file was selected."
            ).show();
        }

    }

    public void btnCancel(ActionEvent actionEvent)
    {
        AuthController authController = new AuthController();
        authController.doLogout();
        this.adminGUI.getMainApp().toLogin();
    }

    public void setMainApp(MainFx mainApp)
    {
        this.mainApp = mainApp;
        this.app = App.getInstance();
    }

    public void setAdminGUI(AdminGUI adminGUI)
    {
        this.adminGUI = adminGUI;
    }
}
