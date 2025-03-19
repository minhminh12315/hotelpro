package dao;

import connect.Connect;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements BaseDao<Customer> {

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer (fullname, phonenumber, email, address, id_passport, dateofbirth, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getPhoneNumber());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getIdPassport());
            statement.setDate(6, Date.valueOf(customer.getDateOfBirth()));
            statement.setString(7, customer.getGender());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET fullname = ?, phonenumber = ?, email = ?, address = ?, id_passport = ?, dateofbirth = ?, gender = ? WHERE customerid = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getPhoneNumber());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getIdPassport());
            statement.setDate(6, Date.valueOf(customer.getDateOfBirth()));
            statement.setString(7, customer.getGender());
            statement.setInt(8, customer.getCustomerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM customer WHERE customerid = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer.getCustomerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE customerid = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customerid"));
                customer.setFullName(resultSet.getString("fullname"));
                customer.setPhoneNumber(resultSet.getString("phonenumber"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setIdPassport(resultSet.getString("id_passport"));
                customer.setDateOfBirth(resultSet.getDate("dateofbirth").toLocalDate());
                customer.setGender(resultSet.getString("gender"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Customer findByBookingId(int bookingId) {
        String sql = "SELECT c.* FROM customer c JOIN booking b ON c.customerid = b.customerid WHERE b.bookingid = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customerid"));
                customer.setFullName(resultSet.getString("fullname"));
                customer.setPhoneNumber(resultSet.getString("phonenumber"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setIdPassport(resultSet.getString("id_passport"));
                customer.setDateOfBirth(resultSet.getDate("dateofbirth").toLocalDate());
                customer.setGender(resultSet.getString("gender"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customerid"));
                customer.setFullName(resultSet.getString("fullname"));
                customer.setPhoneNumber(resultSet.getString("phonenumber"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setIdPassport(resultSet.getString("id_passport"));
                customer.setDateOfBirth(resultSet.getDate("dateofbirth").toLocalDate());
                customer.setGender(resultSet.getString("gender"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}