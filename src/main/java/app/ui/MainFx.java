package app.ui;

import app.controller.App;
import app.ui.gui.LoginUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFx extends Application {

    private final double MINIMUM_WINDOW_WIDTH = 600.0;
    private final double MINIMUM_WINDOW_HEIGHT = 450.0;
    private Stage stage;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        this.stage = stage;
        stage.setTitle("DGS Vaccination Management");
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.setOnCloseRequest(e -> Platform.exit());
        this.toLogin();
        this.stage.show();
    }

    @Override
    public void stop()
    {
        App.getInstance().getCompany().saveAllStores();
    }

    public Stage getStage()
    {
        return this.stage;
    }

    public void toLogin()
    {
        try
        {
            LoginUI loginUI = (LoginUI) this.replaceSceneContent("/fxml/LoginGUI.fxml",0,0);
            loginUI.setMainApp(this);
        } catch (Exception ex)
        {
            Logger.getLogger(MainFx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Initializable replaceSceneContent(String fxml, double width, double height) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainFx.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainFx.class.getResource(fxml));
        Pane page;
        try
        {
            page = loader.load(in);
        } finally
        {
            in.close();
        }
        if(width == 0 || height == 0){
            Scene scene = new Scene(page, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
            scene.getStylesheets().add("/styles/Styles.css");
            this.stage.setScene(scene);
            this.stage.sizeToScene();
            return (Initializable) loader.getController();
        }else {
            Scene scene = new Scene(page, width, height);
            scene.getStylesheets().add("/styles/Styles.css");
            this.stage.setScene(scene);
            this.stage.sizeToScene();
            return (Initializable) loader.getController();
        }
    }

}
