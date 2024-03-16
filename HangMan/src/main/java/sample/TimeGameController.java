package sample;

import database.SQLCommands;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.Random;

public class TimeGameController extends GameClass {
    @FXML
    private Text timer;
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
    public void clearData() {

    }

    @Override
    public void continueGame() {

    }

    @Override
    public void createView() {

    }

    @Override
    public void check() {

    }

    @Override
    public void createButtons() {

    }
}
