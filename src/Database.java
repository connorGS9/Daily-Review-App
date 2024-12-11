import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;
    private static final String url = "jdbc:mysql://127.0.0.1:3306/daily_review";
    private static final String user = "root";
    private static final String password = "ticket";

    private Database() {}

    public static Connection getConnection() {
        try {
            // Load the MySQL driver (for JDBC 4.0 and below, it's good practice to explicitly load the driver)
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
