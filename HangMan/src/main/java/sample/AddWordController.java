package sample;

import database.SQLCommands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWordController {
    private Stage stage;
    void setStage(Stage stage)
    {
        this.stage=stage;
    }
    @FXML
    private TextField wordField;

    public void add() {
        String word=wordField.getText();
        SQLCommands.insertWord(word);
        stage.close();
    }
}
