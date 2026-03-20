package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private final String url = "jdbc:mysql://localhost:3306/prasanadb"; // replace
    private final String user = "root"; // DB username
    private final String password = "prasana@2007"; // DB password

    public Connection getConnection() {
        try {
        	
        	System.out.println("This is working");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return null;
        }
    }


}
