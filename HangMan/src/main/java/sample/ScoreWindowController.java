package sample;

import database.SQLCommands;
import database.TableGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreWindowController extends TableViewClass implements Initializable {
    @FXML
    private TableView<ObservableList<String>> tableView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createView();
    }

    @Override
    public void clearData() {
        SQLCommands.clearTable("scoreTable");
        data.clear();
        tableView.getColumns().clear();

    }

    @Override
    public void createView() {
        data= SQLCommands.getBasicScoreList();
        TableColumn<ObservableList<String>,String> nickColumn= TableGenerator.createColumn("nick",0);
        TableColumn<ObservableList<String>,String> scoreColumn=TableGenerator.createColumn("score",1);
        TableColumn<ObservableList<String>,String> frameColumn=TableGenerator.createColumn("frames",2);
        nickColumn.setPrefWidth(390);
        scoreColumn.setPrefWidth(195);
        scoreColumn.setComparator((el1, el2) -> {
            int score1 = Integer.parseInt(el1);
            int score2 = Integer.parseInt(el2);
            return Integer.compare(score1, score2);
        });
        frameColumn.setComparator((el1, el2) -> {
            int score1 = Integer.parseInt(el1);
            int score2 = Integer.parseInt(el2);
            return Integer.compare(score1, score2);
        });
        frameColumn.setPrefWidth(195);
        tableView.getColumns().add(nickColumn);
        tableView.getColumns().add(scoreColumn);
        tableView.getColumns().add(frameColumn);

        tableView.setItems(data);
    }

    public void changeView() {
    }
}
