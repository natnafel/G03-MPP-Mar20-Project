package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by natnafel on 3/5/20.
 */
public class DBConnectionHelper {
    public static final String DB_CONNECTION_URL = "jdbc:sqlite:src/dataaccess/storage/librarydb.sqlite";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION_URL);
    }
}
