package database;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.DeleteButtonCell;
import sample.MainWordDBController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCommands {
    public static ObservableList<String> getWordList()
    {
        ObservableList<String> list= FXCollections.observableArrayList();
        String command = "SELECT word FROM wordsTable";
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
            Statement statement= conn.createStatement()) {
            ResultSet resultSet=statement.executeQuery(command);
            while (resultSet.next())
                list.add(resultSet.getString(1));
        }
        catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas pobierania listy: " + e.getMessage());
        }
        return list;
    }
    public static void insertWord(String wordToAdd, MainWordDBController mw)
    {
        String command = "INSERT INTO wordsTable (word) VALUES (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             PreparedStatement statement = conn.prepareStatement(command)) {
            statement.setString(1,wordToAdd);
            statement.executeUpdate();
            int size= mw.getDataSize();
            mw.setData(wordToAdd);
            if(size==0)
            {
                TableColumn<String,Void> deleteColumn=new TableColumn<>("Delete");
                deleteColumn.setCellFactory(DeleteButtonCell.forTableColumn());
                TableView<String> tableView=mw.getTableView();
                tableView.getColumns().remove(1);
                deleteColumn.setPrefWidth(200);
                tableView.getColumns().add(deleteColumn);
                mw.setTableView(tableView);
            }
            System.out.println("Słowo zostało dodane.");

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas dodawania wyrazu do tabeli: " + e.getMessage());
        }
    }
    public static int getTableSize(String name)
    {
        int res=0;
        String command= "SELECT COUNT(*) AS rowCount FROM "+name;
        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
            Statement statement= conn.createStatement()) {
            ResultSet resultSet=statement.executeQuery(command);
            //sprawdzenie czy wynik zwrócił jakikolwiek wynik (ustawienie na pierwszą kolumnę wyniku)
            if (resultSet.next())
                res= resultSet.getInt("rowCount");
        }
        catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas wykonania polecenia: " + e.getMessage());
        }
        return res;
    }
    public static void deleteWord(String wordToDelete)
    {
        String command= "Delete FROM wordsTable WHERE word= (?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             PreparedStatement statement = conn.prepareStatement(command)) {
            statement.setString(1,wordToDelete);
            int changedRecords= statement.executeUpdate();
            if(changedRecords>0)
                System.out.println("Słowo zostało usunięte.");
            else
                System.out.println("Nie znaleziono wyrazu");

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas usuwania wyrazu z tabeli: " + e.getMessage());
        }
    }
}
