package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Stage mainStage; //kontener reprezentujący okno aplikacji
    private Scene dbScene; //scena wyswietlana w konterze


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

    public void showGameScene()
    {
        mainStage.setScene(gameScene);
        basicGameController.startGame();
    }


    public void showDBScene()
    {
        mainStage.setScene(dbScene);
    } //wrzucenie sceny do kontenera w celu wyswietlenia

    public void logIn() {
    }

    public void register() {
        mainStage.setScene(registerScene);
    }
}