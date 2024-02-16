package sample;

import database.SQLCommands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class BasicGameController {
    @FXML
    private Button button;
    @FXML
    private Text message;
    @FXML
    private HBox box;
    @FXML
    private HBox liveBox;
    private Stage mainStage;
    private Scene mainScene;
    private final List<Boolean> isKnown=new ArrayList<>();
    private char[] word;
    @FXML
    private TextField letterToCheck;
    private int lives=9;
    private int wordLen;
    @FXML
    private Text used;



    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }


    public void startGame()
    {
        int size=SQLCommands.getTableSize("wordsTable");
        if(size!=0)
        {
            ObservableList<String> list= SQLCommands.getWordList();
            Random random=new Random();
            int number=random.nextInt(list.size());
            String s=list.get(number).toLowerCase();
            word=s.toCharArray();
            wordLen = s.length();

            Image image =  new Image("file:HangMan/src/main/resources/images/blankField.png");
            for(int i = 0; i< wordLen; i++)
            {
                isKnown.add(false);
                ImageView imageView=new ImageView();
                imageView.setFitHeight(100);
                imageView.setFitWidth(75);
                imageView.setImage(image);

                box.getChildren().add(imageView);
            }
            Image catImage =  new Image("file:HangMan/src/main/resources/images/cat_hp.png");
            for(int i = 0; i< 9; i++)
            {
                ImageView imageView=new ImageView();
                imageView.setFitHeight(100);
                imageView.setFitWidth(85);
                imageView.setImage(catImage);
                liveBox.getChildren().add(imageView);
            }
        }
    }
    public void setScene(Scene scene) {
        this.mainScene = scene;
    }
    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }

    public void check() {

        char letter= letterToCheck.getText().toLowerCase().charAt(0);
        letterToCheck.clear();
        boolean find=false;

        for(int i=0;i<wordLen;i++)
        {

            if(!isKnown.get(i))
            {

               if(word[i]==letter)
               {
                  ImageView imageView= (ImageView) box.getChildren().get(i);
                  imageView.setImage(new Image("file:HangMan/src/main/resources/images/litery/"+letter+".jpg"));
                  box.getChildren().set(i,imageView);
                  isKnown.set(i,true);
                  find=true;
               }
            }
        }
        if(!isKnown.contains(false))
        {
            message.setText("WYGRAŁES");
            button.setDisable(true);

        }

        if(!find)
        {
            ImageView imageView= (ImageView) liveBox.getChildren().get(lives-1);
            imageView.setImage(new Image("file:HangMan/src/main/resources/images/deadCat_hp.png"));
            liveBox.getChildren().set(lives-1,imageView);
            lives--;
            used.setText(used.getText()+letter+' ');
            if(lives==0)
            {
                message.setText("PRZEGRAŁES");
                button.setDisable(true);
            }



        }

    }
}
