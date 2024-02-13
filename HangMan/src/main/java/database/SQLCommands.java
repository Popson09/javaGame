package database;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.MainWordDBController;

import java.sql.*;

public class SQLCommands {
    public static void insertWord(String wordToAdd, MainWordDBController mw)
    {
        String command = "INSERT INTO wordsTable (word) VALUES (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             PreparedStatement statement = conn.prepareStatement(command)) {
            statement.setString(1,wordToAdd);
            statement.executeUpdate();
            ResultSet generatedKeys =statement.getGeneratedKeys();
            ObservableList<String>row= FXCollections.observableArrayList();
            generatedKeys.next();
            row.add(String.valueOf(generatedKeys.getLong(1)));
            row.add(wordToAdd);
            mw.setData(row);
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
            {
                System.out.println("Słowo zostało usunięte.");
            }
            else
                System.out.println("Nie znaleziono wyrazu");

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas usuwania wyrazu z tabeli: " + e.getMessage());
        }
    }
    public static void getTableData(String name,TableView<ObservableList<String>> tableView,ObservableList<ObservableList<String>> data)
    {
        String command= "SELECT * FROM "+name;
        try(Connection conn= DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
        Statement statement= conn.createStatement())
        {
            ResultSet resultSet=statement.executeQuery(command);
            int columns = resultSet.getMetaData().getColumnCount();
            for(int i=1;i<=columns;i++)
            {
                //dodanie kolumn do tableview
                TableColumn<ObservableList<String>,String> tableColumn= new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                int finalI = i-1;
                //fabryka pozwalająca pobierać dane z komórek kolumny
                tableColumn.setCellValueFactory(cellData -> {
                    ObservableList<String> row = cellData.getValue();
                    return new SimpleStringProperty(row.get(finalI));
                });
                tableColumn.setStyle("-fx-font-size: 16px;\n" +
                        "    -fx-font-family: \"Arial\";");
                //dodajemy kolumne do widoku
                tableView.getColumns().add(tableColumn);
            }
            while (resultSet.next())
            {
                ObservableList<String> row= FXCollections.observableArrayList();
                for(int i=1;i<=columns;i++)
                {
                    //dodajemy do listy reprezentującej wiersz kolejne kolumny
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas pobrania danych z tabeli: "+ e);
        }
    }
}
