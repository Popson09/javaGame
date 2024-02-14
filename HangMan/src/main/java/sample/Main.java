package sample;

import database.SQLiteConnector;
import database.TableGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        try (Connection conn = SQLiteConnector.connect()) { //inicjalizacja połączenia z bazą
            if (conn != null) {
                System.out.println("Połączono z bazą danych SQLite.");
                TableGenerator.createWordTable();



            }
        } catch (SQLException e) {
            System.out.println("Nie udało się połączyć z bazą danych SQLite: " + e.getMessage());
        }
        //pobranie pliku fxml
        FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        //wczytanie pliku fxml zawierający opis sceny
        Parent mainWindow = mainWindowLoader.load();
        //pobranie kontrolera powiązanego z plikiem
        MainWindowController mainWindowController = mainWindowLoader.getController();
        //podanie do srodka kontrolera głównego okna aplikacji
        mainWindowController.setStage(stage);

        FXMLLoader dbWindowLoader = new FXMLLoader(getClass().getResource("MainWordDB.fxml"));
        Parent dbWindow = dbWindowLoader.load();
        MainWordDBController dbWindowController = dbWindowLoader.getController();
        dbWindowController.setStage(stage);

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("basicGame.fxml"));
        Parent gameWindow = gameLoader.load();
        BasicGameController basicGameController = gameLoader.getController();
        basicGameController.setStage(stage);

        Scene mainScene=new Scene(mainWindow, 800, 600);
        //podanie do kontrolera sceny w celu przełączania jej w oknie na podstawie działań użytkownika
        mainWindowController.setDbScene(new Scene(dbWindow, 800, 600)); // wrzucenie do kontenera bd sceny głównego okna
        mainWindowController.setGameScene(new Scene(gameWindow, 800, 600));
        dbWindowController.setmainScene(mainScene);
        basicGameController.setScene(mainScene);


        stage.setTitle("HangMan Game");
        //wrzucenie sceny do głównego okna
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}