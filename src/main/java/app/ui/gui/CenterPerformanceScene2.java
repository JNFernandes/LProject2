package app.ui.gui;

import app.controller.App;
import app.ui.MainFx;
import app.ui.gui.controller.CoordinatorGUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CenterPerformanceScene2 implements Initializable {

    private MainFx mainApp;

    private App app;
    @FXML
    private TextArea console;

    private PrintStream ps;
    @FXML
    private CoordinatorGUI coordinatorGUI;

    public CenterPerformanceScene2() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ps = new PrintStream(new CenterPerformanceScene2.Console(console)) ;
        System.setOut(ps);
        System.setErr(ps);

    }
    public void setCoordinatorGUI(CoordinatorGUI coordinatorGUI) {
        this.coordinatorGUI = coordinatorGUI;
    }

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }

    public void showResult(LocalDate day, Integer minutes, String email) {
       coordinatorGUI.getCenterPerformanceController().calculatePerformanceBasedOnInterval(minutes, (LocalDate) day, email);
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        coordinatorGUI.toCoordinatorScene1();
    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }
}
