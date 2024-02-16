package sample;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Stage mainStage; //kontener reprezentujący okno aplikacji
    private Scene dbScene; //scena wyswietlana w konterze

    private Scene gameScene;
    private BasicGameController basicGameController;

    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    public void setBasicGameController(BasicGameController basicGameController) {
        this.basicGameController = basicGameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setDbScene(Scene scene) {
        this.dbScene = scene;
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }
    public void showGameScene()
    {
        mainStage.setScene(gameScene);
        basicGameController.startGame();
    }

    public void showDBScene()
    {
        mainStage.setScene(dbScene);
    } //wrzucenie sceny do kontenera w celu wyswietlenia
}