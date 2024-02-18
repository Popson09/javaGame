package database;

import java.sql.*;

public class TableGenerator {
    public static void createWordTable() {
        // SQL zapytanie do sprawdzenia czy tabela istnieje
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='wordsTable';";

        // SQL zapytanie do utworzenia tabeli
        String createTableSQL = "CREATE TABLE wordsTable (\n"
                + "    id INTEGER PRIMARY KEY,\n"
                + "    word TEXT NOT NULL, category TEXT NOT NULL "
                + ");";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             Statement statement = conn.createStatement()) {
            // Sprawdzenie czy tabela już istnieje
            ResultSet resultSet = statement.executeQuery(checkTableExistsSQL);

            // Jeżeli tabela nie istnieje, to ją tworzymy
            if (!resultSet.next()) {
                statement.execute(createTableSQL);
                System.out.println("Tabela została utworzona.");
            } else {
                System.out.println("Tabela już istnieje, nie trzeba tworzyć nowej.");
            }

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas tworzenia lub sprawdzania tabeli: " + e.getMessage());
        }
    }
    public static  void addCategoryColumn()
    {
        String command= "ALTER TABLE wordsTable ADD COLUMN category TEXT NOT NULL DEFAULT 'category'";
        try(Connection conn= DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
        Statement statement= conn.createStatement())
        {
            statement.executeUpdate(command);
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd w trakcie dodawania kolumny: "+e);
        }
    }
}
