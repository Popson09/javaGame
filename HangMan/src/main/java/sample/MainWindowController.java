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
    private Button buttonGame;

    private Stage mainStage; //kontener reprezentujący okno aplikacji
    private Scene dbScene; //scena wyswietlana w konterze
    private Scene loginScene;
    private Scene registerScene;

    private Scene gameScene;
    private BasicGameController basicGameController;
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
        buttonGame.setDisable(true);
        this.nick.set(" ");
        helloMessage.textProperty().bind(Bindings.concat("Zalogowany jako: ").concat(nick));

    }
    public void setDbScene(Scene scene) {
        this.dbScene = scene;
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setRegisterScene(Scene registerScene) {
        this.registerScene = registerScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void showGameScene()
    {
        mainStage.setScene(gameScene);
        basicGameController.setNick(nick.get());
        basicGameController.startGame();

    }


    public void showDBScene()
    {
        mainStage.setScene(dbScene);
    } //wrzucenie sceny do kontenera w celu wyswietlenia

    public void logIn() {

        mainStage.setScene(loginScene);
        if (!nick.get().isEmpty())
            buttonGame.setDisable(false);
    }

    public void register() {
        mainStage.setScene(registerScene);
    }

    public void setNick(String s)
    {
        nick.set(s);
    }
}