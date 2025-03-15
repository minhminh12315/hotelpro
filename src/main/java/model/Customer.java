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
    public Customer(){

    }
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

    static Connection conn = new Connect().getConn();
    public static double getTotalCustomers(){
        double total = 0;
        String query = "SELECT * FROM Customer";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("customerid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public String getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Customer(String fullName, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        this.fullName = fullName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public List<Customer> getCustomersInHotel(){
        String query = "SELECT c.fullname, r.roomnumber, b.checkindate, b.checkoutdate " +
                "FROM Customer as c " +
                "JOIN booking as b on b.customerid = c.customerid " +
                "JOIN room as r on r.roomid = b.roomid";
        List<Customer> customers = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("fullname");
                String roomNumber = rs.getString("roomnumber");
                Date checkInDateSql = rs.getDate("checkindate");
                Date checkOutDateSql = rs.getDate("checkoutdate");

                LocalDate checkInDate = (checkInDateSql != null) ? ((java.sql.Date) checkInDateSql).toLocalDate() : null;
                LocalDate checkOutDate = (checkOutDateSql != null) ? ((java.sql.Date) checkOutDateSql).toLocalDate() : null;

                Customer customer = new Customer(fullName, roomNumber, checkInDate, checkOutDate);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

}