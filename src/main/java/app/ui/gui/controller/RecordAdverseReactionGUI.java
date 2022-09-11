package app.ui.gui.controller;

import app.controller.RecordAdverseReactionController;
import app.domain.model.AdverseReaction;
import app.ui.MainFx;
import app.ui.gui.RecordAdverseReactionScene1;
import app.ui.gui.RecordAdverseReactionScene2;
import app.ui.gui.RecordAdverseReactionScene3;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordAdverseReactionGUI
{

    private final MainFx mainApp;
    private final NurseGUI nurseGUI;
    private final RecordAdverseReactionController recordAdverseReactionController;

    public RecordAdverseReactionGUI(MainFx mainApp, NurseGUI nurseGUI)
    {
        this.mainApp = mainApp;
        this.nurseGUI = nurseGUI;
        this.recordAdverseReactionController = new RecordAdverseReactionController();
    }

    public MainFx getMainApp()
    {
        return this.mainApp;
    }

    public RecordAdverseReactionController getRecordAdverseReactionController()
    {
        return this.recordAdverseReactionController;
    }

    public void toRecordAdverseReactionScene1()
    {
        try
        {
            RecordAdverseReactionScene1 recordAdverseReactionScene1 =
                    (RecordAdverseReactionScene1) this.mainApp
                            .replaceSceneContent("/fxml/RecordAdverseReactionScene1.fxml",0,0);
            recordAdverseReactionScene1.setRecordAdverseReactionGUI(this);
            recordAdverseReactionScene1.setNurseGUI(this.nurseGUI);
            recordAdverseReactionScene1.initComboBox();

            AdverseReaction adverseReaction = this.recordAdverseReactionController.getAdverseReaction();
            if (adverseReaction != null)
            {
                recordAdverseReactionScene1.showAdverseReaction();
            }

            recordAdverseReactionScene1.getTxtSnsNumber().requestFocus();
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toRecordAdverseReactionScene2()
    {
        try
        {
            RecordAdverseReactionScene2 recordAdverseReactionScene2
                    = (RecordAdverseReactionScene2) this.mainApp.
                    replaceSceneContent("/fxml/RecordAdverseReactionScene2.fxml",0,0);
            recordAdverseReactionScene2.setRecordAdverseReactionGUI(this);
            recordAdverseReactionScene2.setNurseGUI(this.nurseGUI);
            recordAdverseReactionScene2.showAdverseReaction();
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toRecordAdverseReactionScene3(String notification)
    {
        try
        {
            RecordAdverseReactionScene3 recordAdverseReactionScene3 =
                    (RecordAdverseReactionScene3) this.mainApp.
                            replaceSceneContent("/fxml/RecordAdverseReactionScene3.fxml",0,0);
            recordAdverseReactionScene3.setNurseGUI(this.nurseGUI);
            recordAdverseReactionScene3.setRecordAdverseReactionGUI(this);
            recordAdverseReactionScene3.showNotification(notification);
        } catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

}
