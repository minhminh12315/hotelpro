package dao;

import model.BookingUsage;
import connect.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingUsageDao implements BaseDao<BookingUsage> {

    @Override
    public void save(BookingUsage bookingUsage) {
        String query = "INSERT INTO BookingUsage (BookingID, ServiceID, ProductID, ServiceUsagePrice, Quantity, UsageDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingUsage.getBookingID());
            preparedStatement.setObject(2, bookingUsage.getServiceID(), Types.INTEGER);
            preparedStatement.setObject(3, bookingUsage.getProductID(), Types.INTEGER);
            preparedStatement.setInt(4, bookingUsage.getServiceUsagePrice());
            preparedStatement.setInt(5, bookingUsage.getQuantity());
            preparedStatement.setDate(6, java.sql.Date.valueOf(bookingUsage.getUsageDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BookingUsage bookingUsage) {
        String query = "UPDATE BookingUsage SET BookingID = ?, ServiceID = ?, ProductID = ?, ServiceUsagePrice = ?, Quantity = ?, UsageDate = ? WHERE BookingUsageID = ?";
        try (Connection connection = Connect.connection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingUsage.getBookingID());
            preparedStatement.setObject(2, bookingUsage.getServiceID(), Types.INTEGER);
            preparedStatement.setObject(3, bookingUsage.getProductID(), Types.INTEGER);
            preparedStatement.setInt(4, bookingUsage.getServiceUsagePrice());
            preparedStatement.setInt(5, bookingUsage.getQuantity());
            preparedStatement.setDate(6, java.sql.Date.valueOf(bookingUsage.getUsageDate()));
            preparedStatement.setInt(7, bookingUsage.getBookingUsageID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BookingUsage bookingUsage) {
        String query = "DELETE FROM BookingUsage WHERE BookingUsageID = ?";
        try (Connection connection = Connect.connection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingUsage.getBookingUsageID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Override
    public BookingUsage findById(int id) {
        String query = "SELECT * FROM BookingUsage WHERE BookingUsageID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                BookingUsage bookingUsage = new BookingUsage();
                bookingUsage.setBookingUsageID(resultSet.getInt("BookingUsageID"));
                bookingUsage.setBookingID(resultSet.getInt("BookingID"));
                bookingUsage.setServiceID((Integer) resultSet.getObject("ServiceID"));
                bookingUsage.setProductID((Integer) resultSet.getObject("ProductID"));
                bookingUsage.setServiceUsagePrice(resultSet.getInt("ServiceUsagePrice"));
                bookingUsage.setQuantity(resultSet.getInt("Quantity"));
                bookingUsage.setUsageDate(resultSet.getDate("UsageDate").toLocalDate());
                return bookingUsage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<BookingUsage> getAll() {
        List<BookingUsage> bookingUsages = new ArrayList<>();
        String query = "SELECT * FROM BookingUsage";
        try (Connection connection = Connect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                BookingUsage bookingUsage = new BookingUsage();
                bookingUsage.setBookingUsageID(resultSet.getInt("BookingUsageID"));
                bookingUsage.setBookingID(resultSet.getInt("BookingID"));
                bookingUsage.setServiceID((Integer) resultSet.getObject("ServiceID"));
                bookingUsage.setProductID((Integer) resultSet.getObject("ProductID"));
                bookingUsage.setServiceUsagePrice(resultSet.getInt("ServiceUsagePrice"));
                bookingUsage.setQuantity(resultSet.getInt("Quantity"));
                bookingUsage.setUsageDate(resultSet.getDate("UsageDate").toLocalDate());
                bookingUsages.add(bookingUsage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingUsages;
    }

    public List<BookingUsage> findByBookingId(int bookingId) {
        List<BookingUsage> bookingUsages = new ArrayList<>();
        String query = "SELECT * FROM BookingUsage WHERE BookingID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookingUsage bookingUsage = new BookingUsage();
                bookingUsage.setBookingUsageID(resultSet.getInt("BookingUsageID"));
                bookingUsage.setBookingID(resultSet.getInt("BookingID"));
                bookingUsage.setServiceID((Integer) resultSet.getObject("ServiceID"));
                bookingUsage.setProductID((Integer) resultSet.getObject("ProductID"));
                bookingUsage.setServiceUsagePrice(resultSet.getInt("ServiceUsagePrice"));
                bookingUsage.setQuantity(resultSet.getInt("Quantity"));
                bookingUsage.setUsageDate(resultSet.getDate("UsageDate").toLocalDate());
                bookingUsages.add(bookingUsage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingUsages;
    }
}