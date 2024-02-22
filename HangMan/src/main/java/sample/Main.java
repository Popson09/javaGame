package sample;

import database.SQLCommands;
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
               // TableGenerator.dropTable("accountTable");
                TableGenerator.createAccountTable();
                TableGenerator.createScoreTable();
               // TableGenerator.addCategoryColumn();
                //SQLCommands.addAccount();



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
        TableViewClass dbWindowController = dbWindowLoader.getController();
        dbWindowController.setMainStage(stage);

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("basicGame.fxml"));
        Parent gameWindow = gameLoader.load();
        BasicGameController basicGameController = gameLoader.getController();
        basicGameController.setStage(stage);

        FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("registerWindow.fxml"));
        Parent registerWindow= registerLoader.load();
        RegisterWindowController registerWindowController = registerLoader.getController();
        registerWindowController.setMainStage(stage);

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
        Parent loginWindow =loginLoader.load();
        LoginWindowController loginWindowController= loginLoader.getController();
        loginWindowController.setMainStage(stage);

        FXMLLoader scoreLoader = new FXMLLoader(getClass().getResource("ScoreWindow.fxml"));
        Parent scoreWindow = scoreLoader.load();
        TableViewClass scoreWindowController = scoreLoader.getController();
        scoreWindowController.setMainStage(stage);

        Scene mainScene=new Scene(mainWindow, 800, 600);
        //podanie do kontrolera sceny w celu przełączania jej w oknie na podstawie działań użytkownika
        mainWindowController.setDbScene(new Scene(dbWindow, 800, 600)); // wrzucenie do kontenera bd sceny głównego okna
        mainWindowController.setGameScene(new Scene(gameWindow, 800, 600));
        mainWindowController.setRegisterScene(new Scene(registerWindow, 800, 600));
        mainWindowController.setLoginScene(new Scene(loginWindow,800,600));
        mainWindowController.setScoreScene(new Scene(scoreWindow,800,600));
        dbWindowController.setMainScene(mainScene);
        basicGameController.setScene(mainScene);
        registerWindowController.setMainScene(mainScene);
        scoreWindowController.setMainScene(mainScene);
        loginWindowController.setMainScene(mainScene);
        loginWindowController.setMw(mainWindowController);
        mainWindowController.setBasicGameController(basicGameController);
        basicGameController.setSw(scoreWindowController);


        stage.setTitle("HangMan Game");
        //wrzucenie sceny do głównego okna
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}