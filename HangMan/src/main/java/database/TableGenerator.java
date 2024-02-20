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

        createTable(checkTableExistsSQL,createTableSQL);
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
    public static void createAccountTable()
    {
        String checkTableExistsSQL = "SELECT name FROM sqlite_master WHERE type='table' AND name='accountTable';";
        String createTableSQL = "CREATE TABLE accountTable ( nick TEXT PRIMARY KEY, password TEXT NOT NULL);";
        createTable(checkTableExistsSQL,createTableSQL);

    }
    public static void createTable(String existCommand,String createCommand)
    {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(existCommand);


            if (!resultSet.next()) {
                statement.execute(createCommand);
                System.out.println("Tabela została utworzona.");
            } else {
                System.out.println("Tabela już istnieje, nie trzeba tworzyć nowej.");
            }

        } catch (SQLException e) {
            System.out.println("Wystąpił błąd podczas tworzenia lub sprawdzania tabeli: " + e.getMessage());
        }
    }
    public static void dropTable(String name)
    {
        try(Connection conn=DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db");
            Statement statement= conn.createStatement())
        {
            statement.executeUpdate("DROP TABLE "+name);
        }
        catch (SQLException e)
        {
            System.out.println("Wystąpił błąd podczas usuwania tabeli "+name+" : "+e);
        }
    }
}
