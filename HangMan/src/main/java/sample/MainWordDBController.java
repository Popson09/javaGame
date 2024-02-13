package sample;

import database.SQLCommands;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainWordDBController implements Initializable {
    private Stage dbStage;
    private Scene mainScene;
    //int reprezentowany jako stan pozwalający na powiązanie z interfejsem
  //  private IntegerProperty number=new SimpleIntegerProperty(0) ;
    private ObservableList<ObservableList<String>> data= FXCollections.observableArrayList();

    @FXML
    private Text wordCount;
    @FXML
    private TableView<ObservableList<String>> tableView;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // number.set(SQLCommands.getTableSize("wordsTable"));
        //ustawienie wartosci w polu text, binding sledzi wartosc number i aktualizuje wyswieytlany napis w zależnosci od wartosci zmiennej
        //wordCount.textProperty().bind(Bindings.concat("Liczba wyrazów w bazie: ").concat(number.asString()));
        SQLCommands.getTableData("wordsTable",tableView,data);
        tableView.getColumns().get(0).setPrefWidth(200);
        tableView.getColumns().get(0).setComparator(( id1,id2 )->{
            Integer id11 = Integer.parseInt((String) id1);
            Integer id12 = Integer.parseInt((String) id2);
            return id11.compareTo(id12);
        });
        tableView.getColumns().get(0).setSortType(TableColumn.SortType.DESCENDING);
        tableView.getColumns().get(1).setPrefWidth(580);
        tableView.getColumns().get(1).setSortType(TableColumn.SortType.DESCENDING);
        tableView.setItems(data);

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
    public void deleteWord() throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("deleteWord.fxml"));
        Parent window= loader.load();
        DeleteWordController deleteWordController= loader.getController();
        stage.setScene(new Scene(window,320,52));
        deleteWordController.setStage(stage);
        deleteWordController.setMw(this);
        stage.show();
    }
    public void setmainScene(Scene scene) {
        this.mainScene = scene;
    }

    public void setStage(Stage stage)
    {
        this.dbStage=stage;
    }
    public void showMainScene() {
        dbStage.setScene(mainScene);
    }

    public void setData(ObservableList<String> row)
    {
        this.data.add(row);
    }
    public void removeData(String id)
    {
        this.data.removeIf(elem ->
        {
            String idEl = elem.get(1);
            return id.equals(idEl);
        });

    }
}
