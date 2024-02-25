package sample;

import database.SQLCommands;
import database.TableGenerator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWordDBController extends TableViewClass implements Initializable {
    @FXML
    private TableView<ObservableList<String>> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createView();
    }

    @Override
    public void clearData()
    {
        SQLCommands.clearTable("wordsTable");
        data.clear();
        //tableView.getColumns().clear();
    }

    @Override
    public void createView() {
        int size=SQLCommands.getTableSize("wordsTable");
        data= SQLCommands.getWordList();
        TableColumn<ObservableList<String>,String> wordColumn= TableGenerator.createColumn("Word",0);
        TableColumn<ObservableList<String>,String> categoryColumn=TableGenerator.createColumn("Category",1);
        tableView.getColumns().add(wordColumn);
        tableView.getColumns().add(categoryColumn);
        createDeleteColumn(size);
        tableView.setItems(data);
    }
    public int getDataSize() {return data.size();}
    public void addWord() throws IOException {
        showAlert();
        Stage stage=new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("AddWord.fxml"));
        Parent window= loader.load();
        AddWordController addWordController= loader.getController();
        stage.setScene(new Scene(window,320,52));
        addWordController.setStage(stage);
        addWordController.setMw(this);
        stage.show();
    }
    private void showAlert()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wybór pliku");
        alert.setContentText("Podaj plik w postaci: wyraz;kategoria nowaLinia");
        alert.show();
        int delayMillis = 10000; // ms
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delayMillis), event -> {
            // Zamknięcie alertu po upływie czasu
            alert.close();
        }));
        timeline.play();
    }
    public void addFromFile()  {


        showAlert();
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
                String[] array =line.split(";");

                if(array.length>=2)
                    SQLCommands.insertWord(array[0],array[1] ,this);

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
        TableColumn<ObservableList<String>, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(200);
        for(int i=0;i<size;i++)
            deleteColumn.setCellFactory(DeleteButtonCell.forTableColumn());
        this.tableView.getColumns().add(deleteColumn);
    }

    public TableView<ObservableList<String>> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<ObservableList<String>> tableView) {
        this.tableView = tableView;
    }
}
