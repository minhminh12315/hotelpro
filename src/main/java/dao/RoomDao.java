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
        String sql = "DELETE FROM Room WHERE roomID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, room.getRoomID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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


