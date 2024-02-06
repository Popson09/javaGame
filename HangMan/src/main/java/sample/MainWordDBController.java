package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void addWord() throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("AddWord.fxml"));
        Parent window= loader.load();
        AddWordController addWordController= loader.getController();
        stage.setScene(new Scene(window,320,52));
        addWordController.setStage(stage);
        stage.show();

    }
}
