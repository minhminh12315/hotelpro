package dao;

import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.Connect;

public class BookingDao implements BaseDao<Booking> {

    @Override
    public void save(Booking booking) {
        String sql = "INSERT INTO booking (customerid, roomid, bookingdate, roomprice, checkindate, checkoutdate, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getCustomerID());
            ps.setInt(2, booking.getRoomID());
            ps.setDate(3, Date.valueOf(booking.getBookingDate()));
            ps.setInt(4, booking.getRoomPrice());
            ps.setDate(5, Date.valueOf(booking.getCheckInDate()));
            ps.setDate(6, Date.valueOf(booking.getCheckOutDate()));
            ps.setString(7, booking.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Booking booking) {
        String sql = "UPDATE booking SET customerid = ?, roomid = ?, bookingdate = ?, roomprice = ?, checkindate = ?, checkoutdate = ?, status = ? WHERE bookingid = ?";
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getCustomerID());
            ps.setInt(2, booking.getRoomID());
            ps.setDate(3, Date.valueOf(booking.getBookingDate()));
            ps.setInt(4, booking.getRoomPrice());
            ps.setDate(5, Date.valueOf(booking.getCheckInDate()));
            ps.setDate(6, Date.valueOf(booking.getCheckOutDate()));
            ps.setString(7, booking.getStatus());
            ps.setInt(8, booking.getBookingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Booking booking) {
        String sql = "DELETE FROM booking WHERE bookingid = ?";
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getBookingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM booking WHERE bookingid = ?";
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("roomid"),
                        rs.getDate("bookingdate").toLocalDate(),
                        rs.getInt("roomprice"),
                        rs.getDate("checkindate").toLocalDate(),
                        rs.getDate("checkoutdate").toLocalDate(),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> getAll() {
        String sql = "SELECT * FROM booking";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = Connect.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("roomid"),
                        rs.getDate("bookingdate").toLocalDate(),
                        rs.getInt("roomprice"),
                        rs.getDate("checkindate") != null ? rs.getDate("checkindate").toLocalDate() : null,
                        rs.getDate("checkoutdate") != null ? rs.getDate("checkoutdate").toLocalDate() : null,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> searchBookings(String searchText) {
        String sql = "SELECT * FROM booking WHERE LOWER(status) LIKE ?";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("roomid"),
                        rs.getDate("bookingdate").toLocalDate(),
                        rs.getInt("roomprice"),
                        rs.getDate("checkindate").toLocalDate(),
                        rs.getDate("checkoutdate").toLocalDate(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public int getActiveBookingIDByRoomID(int roomID) {
        String query = "SELECT BookingID FROM Booking WHERE RoomID = ? AND Status = 'CheckedIn'";
        try (Connection connection = Connect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // return booking;
                return resultSet.getInt("BookingID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Không tìm thấy booking nào
    }
}