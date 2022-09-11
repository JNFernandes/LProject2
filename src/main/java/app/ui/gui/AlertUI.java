package app.ui.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class AlertUI
{
    public static Alert createAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String msg) {
        Alert alert = new Alert(alertType);

        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(msg);

        if (alertType == Alert.AlertType.CONFIRMATION) {
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
        }

        return alert;
    }

}
