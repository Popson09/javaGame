package database;

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
            System.out.println("Słowo zostało dodane.");
            mw.numberPlus1();



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
    public static void deleteWord(String wordToDelete, MainWordDBController mw)
    {
        String command= "Delete FROM wordsTable WHERE word= (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
            PreparedStatement statement = conn.prepareStatement(command)) {
            statement.setString(1,wordToDelete);
            int changedRecords= statement.executeUpdate();
            if(changedRecords>0)
            {
                System.out.println("Słowo zostało usunięte.");
                mw.numberMinus1();
            }
            else
            {
                System.out.println("Nie znaleziono wyrazu");
            }



        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas usuwania wyrazu z tabeli: " + e.getMessage());
        }
    }
}
