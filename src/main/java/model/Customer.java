package model;

import connect.Connect;
import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    private int customerID;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String address;

    private String idPassport;

    private LocalDate dateOfBirth;

    private String gender;

    // JavaFX properties
    private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty nameProperty = new SimpleStringProperty();
//
//    public Customer(){
//
//    }
//
//    public Customer(int customerID, String fullName, String phoneNumber, String email, String address, String idPassport, LocalDate dateOfBirth, String gender) {
//        this.customerID = customerID;
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.address = address;
//        this.idPassport = idPassport;
//        this.dateOfBirth = dateOfBirth;
//        this.gender = gender;
//    }

    // Getters and setters...

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(String idPassport) {
        this.idPassport = idPassport;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }
    public StringProperty nameProperty() {
        return nameProperty;
    }
//
//    Connection conn = new Connect().getConn();
//
//    public List<Customer> getAll() {
//        String sql = "SELECT * FROM customer " +
//                "ORDER BY customerid ASC";
//        List<Customer> customers = new ArrayList<>();
//        try {
//            ResultSet rs = conn.createStatement().executeQuery(sql);
//            while (rs.next()) {
//                Customer customer = new Customer(
//                        rs.getInt("customerid"),
//                        rs.getString("fullname"),
//                        rs.getString("phonenumber"),
//                        rs.getString("email"),
//                        rs.getString("address"),
//                        rs.getString("id_passport"),
//                        rs.getDate("dateofbirth").toLocalDate(),
//                        rs.getString("gender"));
//                customers.add(customer);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return customers;
//    }
//    public Booking getById(Integer id) {
//        String sql = "SELECT * FROM booking WHERE customerid = ?";
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new Booking(
//                        rs.getInt("bookingid"),
//                        rs.getInt("customerid"),
//                        rs.getInt("roomid"),
//                        rs.getDate("bookingdate").toLocalDate(),
//                        rs.getBigDecimal("roomprice"),
//                        rs.getDate("expectedcheckindate").toLocalDate(),
//                        rs.getDate("expectedcheckoutdate").toLocalDate(),
//                        rs.getDate("checkindate") != null ? rs.getDate("checkindate").toLocalDate() : null,
//                        rs.getDate("checkoutdate") != null ? rs.getDate("checkoutdate").toLocalDate() : null,
//                        rs.getString("status")
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//

}