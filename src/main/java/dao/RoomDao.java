package dao;

import connect.Connect;
import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao implements BaseDao<Room> {

    @Override
    public void save(Room room) {
        String sql = "INSERT INTO Room (roomNumber, roomType, price, capacity) VALUES (?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setBigDecimal(3, room.getPrice());
            statement.setInt(4, room.getCapacity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Room room) {
        String sql = "UPDATE Room SET roomNumber = ?, roomType = ?, price = ?, capacity = ?, status = ? WHERE roomID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setBigDecimal(3, room.getPrice());
            statement.setInt(4, room.getCapacity());
            statement.setString(5, room.getStatus());
            statement.setInt(6, room.getRoomID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Room room) {
        Connection connection = null;
        PreparedStatement deleteRoomStmt = null;

        try {
            connection = Connect.connection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Xóa phòng
            String deleteRoomSql = "DELETE FROM Room WHERE roomNumber = ?";
            deleteRoomStmt = connection.prepareStatement(deleteRoomSql);
            deleteRoomStmt.setString(1, String.valueOf(room.getRoomNumber()));
            int rowsAffected = deleteRoomStmt.executeUpdate();

            if (rowsAffected > 0) {
                connection.commit(); // Commit transaction nếu thành công
                System.out.println("✅ Phòng " + room.getRoomNumber() + " đã được xóa thành công.");
            } else {
                connection.rollback(); // Rollback nếu không tìm thấy phòng
                System.out.println("⚠ Không tìm thấy phòng có số: " + room.getRoomNumber());
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi
                    System.out.println("⚠ Đã rollback do lỗi xảy ra.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            System.out.println("❌ Lỗi khi xóa phòng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (deleteRoomStmt != null) deleteRoomStmt.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public Room findById(int id) {
        Room room = null;
        String sql = "SELECT * FROM Room WHERE roomID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    room = new Room();
                    room.setRoomID(resultSet.getInt("roomID"));
                    room.setRoomNumber(resultSet.getInt("roomNumber"));
                    room.setRoomType(resultSet.getString("roomType"));
                    room.setPrice(resultSet.getBigDecimal("price"));
                    room.setCapacity(resultSet.getInt("capacity"));
                    room.setStatus(resultSet.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    @Override
    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room order by roomNumber asc";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setRoomNumber(resultSet.getInt("roomNumber"));
                room.setRoomType(resultSet.getString("roomType"));
                room.setPrice(resultSet.getBigDecimal("price"));
                room.setCapacity(resultSet.getInt("capacity"));
                room.setStatus(resultSet.getString("status"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;

    }


}


