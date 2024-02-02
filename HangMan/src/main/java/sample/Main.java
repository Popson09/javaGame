package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent mainWindow = mainWindowLoader.load();
        MainWindowController mainWindowController = mainWindowLoader.getController();
        mainWindowController.setStage(stage);

        FXMLLoader dbWindowLoader = new FXMLLoader(getClass().getResource("MainWordDB.fxml"));
        Parent dbWindow = dbWindowLoader.load();
        MainWordDBController dbWindowController = dbWindowLoader.getController();
        dbWindowController.setStage(stage);

        Scene mainScene=new Scene(mainWindow, 800, 600);
        mainWindowController.setDbScene(new Scene(dbWindow, 800, 600));
        dbWindowController.setmainScene(mainScene);


        stage.setTitle("HangMan Game");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}