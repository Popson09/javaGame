package sample;

import database.SQLCommands;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWordDBController implements Initializable {
    private Stage dbStage;
    private Scene mainScene;



    //int reprezentowany jako stan pozwalający na powiązanie z interfejsem
  //  private IntegerProperty number=new SimpleIntegerProperty(0) ;
    private final ObservableList<String> data= FXCollections.observableArrayList();

    @FXML
    private Text wordCount;
    @FXML
    private TableView<String> tableView;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // number.set(SQLCommands.getTableSize("wordsTable"));
        //ustawienie wartosci w polu text, binding sledzi wartosc number i aktualizuje wyswieytlany napis w zależnosci od wartosci zmiennej
        //wordCount.textProperty().bind(Bindings.concat("Liczba wyrazów w bazie: ").concat(number.asString()));
        int size=SQLCommands.getTableSize("wordsTable");
        SQLCommands.createWordTableView("wordsTable",tableView,data);
        tableView.getColumns().get(0).setPrefWidth(580);
        tableView.getColumns().get(0).setSortType(TableColumn.SortType.DESCENDING);
        createDeleteColumn(size);
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

    public void setData(String row)
    {
        this.data.add(row);
    }

    public void addFromFile()  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z wyrazami do dodania");
        File file = fileChooser.showOpenDialog(null);
        int len=data.size();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String line= reader.readLine();
            while(line!=null)
            {
                //podzizał po znakach białych oraz przecinkach
                String[] array =line.split("[,.;:\\s+]");
                for (String s : array) {
                    if (s.isEmpty())
                        continue;

                    SQLCommands.insertWord(s.trim(), this);
                }
                line= reader.readLine();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku: "+e);
        } catch (IOException e) {
            System.out.println("Nastąpił błąd podczas czytania danych z pliku: "+e);
        }

    }
    private void createDeleteColumn(int size)
    {
        TableColumn<String, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(200);
        for(int i=0;i<size;i++)
            deleteColumn.setCellFactory(DeleteButtonCell.forTableColumn());
        this.tableView.getColumns().add(deleteColumn);
    }
    public int getDataSize()
    {
        return data.size();
    }

    public TableView<String> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<String> tableView) {
        this.tableView = tableView;
    }
    /* public void removeData(String id)
    {
        this.data.removeIf(elem ->
        {
            String idEl = elem.get(1);
            return id.equals(idEl);
        });

    }*/
}
