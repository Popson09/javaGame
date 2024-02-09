package sample;

import database.SQLCommands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteWordController {
    @FXML
    TextField wordField;
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

    public void del() {
        String word=wordField.getText();
        SQLCommands.deleteWord(word,mw);
        stage.close();
    }
}
