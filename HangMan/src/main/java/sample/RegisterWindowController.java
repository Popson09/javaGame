package sample;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterWindowController {
    private Scene mainScene;
    private Stage mainStage;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
}
