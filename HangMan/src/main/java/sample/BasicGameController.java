package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BasicGameController implements Initializable {

    private Stage mainStage;
    private Scene mainScene;

    @FXML
    private HBox hBox;
    private List<ImageView> letterList=new ArrayList<>();




    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void setScene(Scene scene) {
        this.mainScene = scene;
    }
    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
}
