package sample;

import database.SQLCommands;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWordController {
    private Stage stage;
    private MainWordDBController mw;
    void setMw(MainWordDBController mw)
    {
        this.mw=mw;
    }
    void setStage(Stage stage)
    {
        this.stage=stage;
    }
    @FXML
    private TextField wordField;

    public void add() {
        String word=wordField.getText();
        SQLCommands.insertWord(word,mw);
        stage.close();
    }


}
