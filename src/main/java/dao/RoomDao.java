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
        PreparedStatement deleteBookingsStmt = null;
        PreparedStatement deleteRoomStmt = null;

        try {
            connection = Connect.connection();
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // 1. Xóa tất cả booking liên quan đến phòng
            String deleteBookingsSql = "DELETE FROM Booking WHERE roomNumber = ?";
            deleteBookingsStmt = connection.prepareStatement(deleteBookingsSql);
            deleteBookingsStmt.setString(1, String.valueOf(room.getRoomNumber()));
            deleteBookingsStmt.executeUpdate();

            // 2. Xóa phòng
            String deleteRoomSql = "DELETE FROM Room WHERE roomNumber = ?";
            deleteRoomStmt = connection.prepareStatement(deleteRoomSql);
            deleteRoomStmt.setString(1, String.valueOf(room.getRoomNumber()));
            int rowsAffected = deleteRoomStmt.executeUpdate();
            // Commit transaction nếu thành công
            connection.commit();

            if (rowsAffected > 0) {
                System.out.println("Phòng " + room.getRoomNumber() + " và các đặt phòng liên quan đã được xóa thành công.");
            } else {
                System.out.println("Không tìm thấy phòng có số: " + room.getRoomNumber());
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // Rollback nếu có lỗi
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            System.out.println("Lỗi khi xóa phòng và các đặt phòng liên quan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (deleteBookingsStmt != null) deleteBookingsStmt.close();
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


