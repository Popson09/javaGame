package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Stage mainStage;
    private Scene dbScene;

    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setDbScene(Scene scene) {
        this.dbScene = scene;
    }
    public void showDBScene()
    {
        mainStage.setScene(dbScene);
    }
}