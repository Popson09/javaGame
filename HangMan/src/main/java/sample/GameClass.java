package sample;

import database.SQLCommands;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public abstract class GameClass {
    protected ObservableList<ObservableList<String>> list; //lista, do której pobierana jest zawartość bazy wyrazów

    protected Stage mainStage; //główne okno aplikacji
    protected Scene mainScene; //scena menu głównego
    protected final List<Boolean> isKnown=new ArrayList<>(); //lista sprawdzająca, które litery zostały zgadnięte
    protected char[] word;//wyraz do zgadnięcia w postaci listy char
    protected int frames; //liczba liter na całym poziomie (do bazy danych)
    protected final SimpleIntegerProperty wordCount= new SimpleIntegerProperty(); //informacja o postępie poziomu
    protected  String nick; //pseudonim gracza
    protected final SimpleIntegerProperty intScore= new SimpleIntegerProperty(); //informacja o wyniku
    protected TableViewClass sw; //klasa kontrolera potrzebna dla aktualizacji table view dla bazy wyników
    protected int combo; //mnożnik combo dla wyniku

    public abstract void clearData(); //czyszczenie okna gry, ustawienie wszystkich pól na default
    public abstract void continueGame(); //wyczyszczenie tylko niektórych pól zostawiając informacje o wyniku i długości rpzgrywki
    public abstract  void createView();
    public abstract void  check() ;
    public abstract void createButtons();

    public void startGame()
    {
        clearData(); //wyczyszczenie planszy
        int size= SQLCommands.getTableSize("wordsTable");
        if(size!=0)
        {
            list= SQLCommands.getWordList();//pobranie wyrazów
            createView();//rozpoczęcie gry

        }

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
