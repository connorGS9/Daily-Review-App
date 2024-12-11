import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;
    private static final String url = "Your Database URL 🌎";
    private static final String user = "Your Database Username 🔱";
    private static final String password = "Your Database Password 🤐";

    private Database() {}

    public static Connection getConnection() {
        try {
            // Load the MySQL driver (for JDBC 4.0 and below, it's good practice to explicitly load the driver) You will likely also need to add the SQL/J .jar from: https://dev.mysql.com/downloads/connector/j/ to your modulepath or library
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully!");

        } catch (Exception e) {
            System.out.println("There was an error connecting to the database, ensure connection credentials are correct.");
            e.printStackTrace();
        }
        return connection;
    }
}
