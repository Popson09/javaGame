package database;

import java.sql.*;

public class SQLCommands {
    public static void insertWord(String wordToAdd)
    {
        String command = "INSERT INTO wordsTable (word) VALUES (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             PreparedStatement statement = conn.prepareStatement(command)) {
            statement.setString(1,wordToAdd);
            statement.executeUpdate();
            System.out.println("Słowo zostało dodane.");


        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas dodawania wyrazu do tabeli: " + e.getMessage());
        }
    }
}
