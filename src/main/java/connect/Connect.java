package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/HotelPro";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Kết nối thành công!");
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
    }

    public static Connection connection() {
        return null;
    }
}