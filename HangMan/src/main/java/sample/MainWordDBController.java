package sample;

import database.SQLCommands;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWordDBController implements Initializable {
    private Stage dbStage;
    private Scene mainScene;
    //int reprezentowany jako stan pozwalający na powiązanie z interfejsem
    private IntegerProperty number=new SimpleIntegerProperty(0) ;

    @FXML
    private Text wordCount;

    public void setStage(Stage stage)
    {
        this.dbStage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.set(SQLCommands.getTableSize("wordsTable"));
        //ustawienie wartosci w polu text, binding sledzi wartosc number i aktualizuje wyswieytlany napis w zależnosci od wartosci zmiennej
        wordCount.textProperty().bind(Bindings.concat("Liczba wyrazów w bazie: ").concat(number.asString()));

    }
    public void setmainScene(Scene scene) {
        this.mainScene = scene;
    }

    public void showMainScene() {
        dbStage.setScene(mainScene);
    }

    public void addWord() throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("AddWord.fxml"));
        Parent window= loader.load();
        AddWordController addWordController= loader.getController();
        stage.setScene(new Scene(window,320,52));
        addWordController.setStage(stage);
        addWordController.setMw(this);
        stage.show();

    }
    public void numberPlus1()
    {
        number.set(number.get() + 1);
    }
    public void numberMinus1()
    {
        if(number.get()>0)
            number.set(number.get() - 1);
    }
}
