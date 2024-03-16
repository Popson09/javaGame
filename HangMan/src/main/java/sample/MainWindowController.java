package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private Button basicGame;
    @FXML
    private Button timeGame;

    private Stage mainStage; //kontener reprezentujący okno aplikacji
    private Scene dbScene; //scena wyswietlana w konterze
    private Scene loginScene;
    private Scene registerScene;

    private Scene basicGameScene;
    private Scene timeGameScene;
    private Scene scoreScene;
    private BasicGameController basicGameController;
    private TimeGameController timeGameController;
    @FXML
    private Text helloMessage;
    private SimpleStringProperty nick=new SimpleStringProperty();

    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    public void setBasicGameController(BasicGameController basicGameController) {
        this.basicGameController = basicGameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        basicGame.setDisable(true);
        timeGame.setDisable(true);
        this.nick.set(" ");
        helloMessage.textProperty().bind(Bindings.concat("Zalogowany jako: ").concat(nick));

    }

    public void setTimeGameScene(Scene timeGameScene) {
        this.timeGameScene = timeGameScene;
    }

    public void setTimeGameController(TimeGameController timeGameController) {
        this.timeGameController = timeGameController;
    }

    public void setDbScene(Scene scene) {
        this.dbScene = scene;
    }

    public void setBasicGameScene(Scene gameScene) {
        this.basicGameScene = gameScene;
    }

    public void setRegisterScene(Scene registerScene) {
        this.registerScene = registerScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setScoreScene(Scene scoreScene) {
        this.scoreScene = scoreScene;
    }

    public void showBasicScene()
    {
        mainStage.setScene(basicGameScene);
        basicGameController.setNick(nick.get());
        basicGameController.startGame();

    }
    public void showTimeScene() {
        mainStage.setScene(timeGameScene);
        timeGameController.setNick(nick.get());
        timeGameController.startGame();
    }




        public void showDBScene()
    {
        mainStage.setScene(dbScene);
    } //wrzucenie sceny do kontenera w celu wyswietlenia

    public void showScoreScene()
    {
        mainStage.setScene(scoreScene);
    }

    public void logIn() {

        mainStage.setScene(loginScene);
        if (!nick.get().isEmpty())
        {
            basicGame.setDisable(false);
            timeGame.setDisable(false);
        }

    }

    public void register() {
        mainStage.setScene(registerScene);
    }

    public void setNick(String s)
    {
        nick.set(s);
    }
}