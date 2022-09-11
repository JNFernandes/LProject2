package app.ui.gui;

import app.controller.AuthController;
import app.domain.model.AdverseReaction;
import app.ui.gui.controller.NurseGUI;
import app.ui.gui.controller.RecordAdverseReactionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordAdverseReactionScene2 implements Initializable {

    private NurseGUI nurseGUI;
    private RecordAdverseReactionGUI recordAdverseReactionGUI;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblAdverseReaction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
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

    public void showAdverseReaction()
    {
        AdverseReaction adverseReaction = this.recordAdverseReactionGUI.getRecordAdverseReactionController().getAdverseReaction();
        this.lblAdverseReaction.setMinWidth(adverseReaction.toString().length());
        this.lblAdverseReaction.setText(adverseReaction.toString());
    }

    @FXML
    private void btnConfirmAction(ActionEvent event)
    {
        boolean registered = this.recordAdverseReactionGUI.
                getRecordAdverseReactionController().addAdverseReaction();

        String notification;

        if (registered)
        {
            notification = "Adverse reaction registered.";
        } else
        {
            notification = "Failed to register adverse reaction.";
        }

        this.recordAdverseReactionGUI.toRecordAdverseReactionScene3(notification);
        this.recordAdverseReactionGUI.getRecordAdverseReactionController().setAdverseReaction(null);
    }

    @FXML
    private void btnReturnAction(ActionEvent event)
    {
        this.recordAdverseReactionGUI.toRecordAdverseReactionScene1();
    }

    @FXML
    private void btnCancelAction(ActionEvent event)
    {
        //AuthController authController = new AuthController();
        //authController.doLogout();
        //this.recordAdverseReactionGUI.getMainApp().toLogin();
        this.nurseGUI.toNurseScene1();
    }

}
