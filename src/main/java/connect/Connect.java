package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String URL = "jdbc:postgresql://localhost:5432/hotelpro"; // Thay 'hotel_db' bằng tên database của bạn
    private static final String USER = "postgres"; // Thay bằng username của bạn
    private static final String PASSWORD = "0804"; // Thay bằng mật khẩu của bạn
    private Connection conn;

    public static Connection connection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to PostgreSQL: " + e.getMessage());
            return null;
        }
    }

    public static boolean checkTable(String customer) {
        if (customer.equals("Customer")) {
            return true;
        } else {
            return false;
        }
    }

    public Connect(){
        this.conn = connection();
    }

    public Connection getConn() {
        return conn;
    }
}