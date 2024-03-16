package sample;

import database.SQLCommands;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Random;

public class TimeGameController extends GameClass {
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
    private HBox endBox; //box w którym pojawiają się przyciski po końcu gry
    @FXML
    private TextField letterToCheck; //pole do wprowadzenia litery
    @FXML
    private Text used; // pole do wyświetlenia zużytych liter
    @FXML
    private Text categoryText; //pole do wyświetlenia kategorii wyrazu





    @Override
    public void clearData() //czyszczenie okna gry, ustawienie wszystkich pól na default
    {
        frames=0;
        wordCount.set(1);
        endBox.getChildren().clear();

        box.getChildren().clear();
        button.setDisable(false);
        used.setText("");
        message.setText("");
        categoryText.setText("");
        combo=1;
        intScore.set(0);
        score.textProperty().bind(Bindings.concat("Wynik Poziomu: ").concat(intScore.asString()));
        gameLen.textProperty().bind(Bindings.concat("Postęp poziomu: wyraz ").concat(wordCount.asString()).concat("/5"));

        isKnown.clear();
    }
    @Override
    public void continueGame() //wyczyszczenie tylko niektórych pól zostawiając informacje o wyniku i długości rpzgrywki
    {

        isKnown.clear();endBox.getChildren().clear();

        box.getChildren().clear();
        used.setText("");
        message.setText("");
        categoryText.setText("");
        wordCount.set(wordCount.get()+1);
        createView();

    }

    @Override
    public void createView()
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

        }
    }


    @Override
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


            used.setText(used.getText()+letter+' ');
            if(combo==1)
            {
                for(int i=0;i< word.length;i++) {
                   ImageView imageView= (ImageView) box.getChildren().get(i);
                    imageView.setImage(new Image("file:HangMan/src/main/resources/images/litery/"+word[i]+".jpg"));
                    box.getChildren().set(i,imageView);

                }


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
    @Override
    public void createButtons()
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
