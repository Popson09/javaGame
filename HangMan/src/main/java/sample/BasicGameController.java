package sample;

import database.SQLCommands;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class BasicGameController {
    @FXML
    private Button button;
    @FXML
    private Text message;
    @FXML
    private Text score;
    @FXML
    private HBox box;
    @FXML
    private HBox liveBox;
    @FXML
    private HBox endBox;
    @FXML
    private TextField letterToCheck;
    @FXML
    private Text used;
    private Stage mainStage;
    private Scene mainScene;
    private final List<Boolean> isKnown=new ArrayList<>();
    private char[] word;
    private int lives;
    private int wordLen;
    private final SimpleIntegerProperty intScore= new SimpleIntegerProperty();

    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }
    private int combo;
    private void clearData()
    {
        endBox.getChildren().clear();
        liveBox.getChildren().clear();
        box.getChildren().clear();
        button.setDisable(false);
        used.setText("");
        message.setText("");
        combo=1;
        intScore.set(0);
        score.textProperty().bind(Bindings.concat("Wynik Poziomu: ").concat(intScore.asString()));
        lives=9;
        isKnown.clear();
    }

    public void startGame()
    {
        clearData();
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

        if(letterToCheck.getText().isEmpty())
        {
            System.out.println("Nie podałes litery!");
            return;
        }
        char letter= letterToCheck.getText().toLowerCase().charAt(0);

        letterToCheck.clear();
        boolean find=false;

        for(int i=0;i<wordLen;i++)
        {

            if(!isKnown.get(i))
            {

               if(word[i]==letter)
               {
                   intScore.set(intScore.get()+10*combo);
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
            message.setFill(Paint.valueOf("#708238"));
            button.setDisable(true);
            createButtons();

        }

        if(!find)
        {
            combo=1;
            ImageView imageView= (ImageView) liveBox.getChildren().get(lives-1);
            imageView.setImage(new Image("file:HangMan/src/main/resources/images/deadCat_hp.png"));
            liveBox.getChildren().set(lives-1,imageView);
            lives--;
            used.setText(used.getText()+letter+' ');
            if(lives==0)
            {
                message.setText("PRZEGRAŁES");
                message.setFill(Paint.valueOf("#CD5C5C"));
                button.setDisable(true);
                createButtons();
            }

        }
        else
            combo++;

    }
    private void createButtons()
    {
        Button backButton=new Button("Powrót");
        backButton.getStyleClass().add("backButton");
        backButton.setPrefSize(150,50);
        backButton.setOnAction(event ->{
            showMainScene();
        });
        Button regame = new Button("Zagraj Ponownie");
        regame.getStyleClass().add("applyButton");
        regame.setPrefSize(150,50);
        regame.setOnAction(event->{
            startGame();
        });
        endBox.getChildren().addAll(backButton,regame);
    }
}
