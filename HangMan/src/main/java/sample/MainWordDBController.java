package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWordDBController implements Initializable {
    private Stage dbStage;
    private Scene mainScene;

    public void setStage(Stage stage)
    {
        this.dbStage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setmainScene(Scene scene) {
        this.mainScene = scene;
    }

    public void showMainScene() {
        dbStage.setScene(mainScene);
    }

    public void addWord() {
    }
}
