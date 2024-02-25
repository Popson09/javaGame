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
public class BasicGameController  {
    @FXML
    private Button button; //przycisk sprawdzający literę
    @FXML
    private Text message; //informacja końca gry
    @FXML
    private Text score; //bierzący wynik poziomu
    @FXML
    private Text gameLen; //informacja o długości gry
    @FXML
    private HBox box; //box z literami wyrazu
    @FXML
    private HBox liveBox; //box z życio-kotami
    @FXML
    private HBox endBox; //box w którym pojawiają się przyciski po końcu gry
    @FXML
    private TextField letterToCheck; //pole do wprowadzenia litery
    @FXML
    private Text used; // pole do wyświetlenia zużytych liter
    @FXML
    private Text categoryText; //pole do wyświetlenia kategorii wyrazu
    private ObservableList<ObservableList<String>> list; //lista, do której pobierana jest zawartość bazy wyrazów

    private Stage mainStage; //główne okno aplikacji
    private Scene mainScene; //scena menu głównego
    private final List<Boolean> isKnown=new ArrayList<>(); //lista sprawdzająca, które litery zostały zgadnięte
    private char[] word;//wyraz do zgadnięcia w postaci listy char
    private int lives; //liczba żyć

    private int frames; //liczba liter na całym poziomie (do bazy danych)
    private final SimpleIntegerProperty wordCount= new SimpleIntegerProperty(); //informacja o postępie poziomu
    private  String nick; //pseudonim gracza
    private final SimpleIntegerProperty intScore= new SimpleIntegerProperty(); //informacja o wyniku
    private TableViewClass sw; //klasa kontrolera potrzebna dla aktualizacji table view dla bazy wyników
    private int combo; //mnożnik combo dla wyniku



    private void clearData() //czyszczenie okna gry, ustawienie wszystkich pól na default
    {
        frames=0;
        wordCount.set(1);
        endBox.getChildren().clear();
        liveBox.getChildren().clear();
        box.getChildren().clear();
        button.setDisable(false);
        used.setText("");
        message.setText("");
        categoryText.setText("");
        combo=1;
        intScore.set(0);
        score.textProperty().bind(Bindings.concat("Wynik Poziomu: ").concat(intScore.asString()));
        gameLen.textProperty().bind(Bindings.concat("Postęp poziomu: wyraz ").concat(wordCount.asString()).concat("/5"));
        lives=9;
        isKnown.clear();
    }
    private void continueGame() //wyczyszczenie tylko niektórych pól zostawiając informacje o wyniku i długości rpzgrywki
    {
        intScore.set(intScore.get()+lives*20);
        lives=9;
        isKnown.clear();endBox.getChildren().clear();
        liveBox.getChildren().clear();
        box.getChildren().clear();
        used.setText("");
        message.setText("");
        categoryText.setText("");
        wordCount.set(wordCount.get()+1);
        createView();

    }

    public void startGame()
    {
        clearData(); //wyczyszczenie planszy
        int size=SQLCommands.getTableSize("wordsTable");
        if(size!=0)
        {
            list= SQLCommands.getWordList();//pobranie wyrazów
            createView();//rozpoczęcie gry

        }

    }
    private void createView()
    {
        Random random=new Random();
        int number=random.nextInt(list.size());
        String s=list.get(number).get(0).toLowerCase(); //wylosowanie wyrazu
        categoryText.setText("Kategoria: "+list.get(number).get(1));
        word=s.toCharArray();
        frames+=s.length();

        Image image =  new Image("file:HangMan/src/main/resources/images/blankField.png");
        for(int i = 0; i< word.length; i++)//stworzenie kafelków z literami
        {
            isKnown.add(false);
            ImageView imageView=new ImageView();
            imageView.setFitHeight(100);
            imageView.setFitWidth(75);
            imageView.setImage(image);

            box.getChildren().add(imageView);
        }
        Image catImage =  new Image("file:HangMan/src/main/resources/images/cat_hp.png");
        for(int i = 0; i< 9; i++) //narysowanie żyć
        {
            ImageView imageView=new ImageView();
            imageView.setFitHeight(100);
            imageView.setFitWidth(85);
            imageView.setImage(catImage);
            liveBox.getChildren().add(imageView);
        }
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

        for(int i=0;i< word.length;i++)
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
            if(wordCount.get()<5)
            {
                continueGame();
            }
            else
            {
                message.setText("WYGRAŁES");
                message.setFill(Paint.valueOf("#708238"));
                button.setDisable(true);
                createButtons();
                SQLCommands.setScore(nick,intScore.intValue(),frames,sw);
            }


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
                SQLCommands.setScore(nick,intScore.intValue(),word.length,sw);
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

    //getery i setery
    public void setStage(Stage stage)
    {
        this.mainStage=stage;
    }

    public void setScene(Scene scene) {
        this.mainScene = scene;
    }
    public void showMainScene()
    {
        mainStage.setScene(mainScene);
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSw(TableViewClass sw) {
        this.sw = sw;
    }
}
