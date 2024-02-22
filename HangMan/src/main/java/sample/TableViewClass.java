package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public abstract class TableViewClass {
    protected Stage mainStage;
    protected Scene mainScene;
    protected ObservableList<ObservableList<String>> data= FXCollections.observableArrayList();


    public void setMainStage(Stage dbStage) {this.mainStage = dbStage;}
    public void setMainScene(Scene mainScene) {this.mainScene = mainScene;}
    public void showMainScene() {mainStage.setScene(mainScene);}
    public void setData( ObservableList<String> row)
    {
        this.data.add(row);
    }
    public abstract void clearData();
    public abstract void createView();
}
