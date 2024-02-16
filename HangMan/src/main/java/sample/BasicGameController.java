package sample;

import database.SQLCommands;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class BasicGameController implements Initializable {
    @FXML
    private HBox box;
    private Stage mainStage;
    private Scene mainScene;
    private int wordLen;
    private String word;



    private final List<ImageView> letterList=new ArrayList<>();




    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list= SQLCommands.getWordList();
        Random random=new Random();
        int number=random.nextInt(list.size());
        word=list.get(number);
        wordLen=word.length();

        Image image =  new Image("file:HangMan/src/main/resources/images/blankField.png");
        for(int i=0;i<wordLen;i++)
        {
            ImageView imageView=new ImageView();
            imageView.setFitHeight(100);
            imageView.setFitWidth(75);
            imageView.setImage(image);
            letterList.add(imageView);
            box.getChildren().add(imageView);
        }


    }
    public void setScene(Scene scene) {
        this.mainScene = scene;
    }
    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
}
