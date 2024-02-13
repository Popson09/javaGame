package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLiteConnector {
    public static Connection connect()
    {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:HangMan/src/main/resources/myDatabase.db"); //połączenie z bazą danych

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
