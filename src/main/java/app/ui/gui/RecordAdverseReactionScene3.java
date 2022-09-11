package app.ui.gui;

import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdverseReactionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordAdverseReactionScene3 implements Initializable {

    private NurseGUI nurseGUI;
    private RecordAdverseReactionGUI recordAdverseReactionGUI;

    @FXML
    private Button btnOK;
    @FXML
    private Label lblNotification;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void setNurseGUI(NurseGUI nurseGUI)
    {
        this.nurseGUI = nurseGUI;
    }

    public void setRecordAdverseReactionGUI(RecordAdverseReactionGUI recordAdverseReactionGUI)
    {
        this.recordAdverseReactionGUI = recordAdverseReactionGUI;
    }

    @FXML
    private void btnOKAction(ActionEvent event)
    {
        //AuthController authController = new AuthController();
        //authController.doLogout();
        //this.recordAdverseReactionGUI.getMainApp().toLogin();
        this.nurseGUI.toNurseScene1();
    }

    public void showNotification(String notification)
    {
        this.lblNotification.setText(notification);
    }

}
