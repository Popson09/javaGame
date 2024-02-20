package sample;

import database.SQLCommands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindowController {
    private Scene mainScene;
    private Stage mainStage;
    private MainWindowController mw;
    @FXML
    private TextField nick;
    @FXML
    private TextField password;
    @FXML
    private Text mess;

    @FXML
    private Button button;

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMw(MainWindowController mw) {
        this.mw = mw;
    }

    private void showMainScene()
    {
        mainStage.setScene(mainScene);
    }
    public void checkAccount() {
        String s= SQLCommands.getAccount(nick.getText(),password.getText());
        if(s.isEmpty())
        {
            mess.setText("Login lub hasło niepoprawne");
            mess.setFill(Color.RED);
        }
        else
        {
            mw.setNick(s);
            showMainScene();
        }
    }
}
