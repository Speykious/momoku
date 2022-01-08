package momoku.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static final Connection connection = connect();
    
    private static Connection connect() {
        try {
            String password = Files.readString(Path.of("db/password.txt"), StandardCharsets.UTF_8);
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/MomokuDB?user=root&password=" + password);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
