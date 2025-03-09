package model;

import connect.Connect;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private int bookingID;

    private int customerid;

    private int roomid;

    private LocalDate bookingDate;

    private int roomPrice;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String status;

    public Booking() {

    }

    public Booking(int bookingID, int customerid, int roomid, LocalDate bookingDate, int roomprice, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.bookingID = bookingID;
        this.customerid = customerid;
        this.roomid = roomid;
        this.bookingDate = bookingDate;
        this.roomPrice = roomprice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // Getters and setters...

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getCustomerID() {
        return customerid;
    }

    public void setCustomerID(int customerid) {
        this.customerid = customerid;
    }

    public int getRoomID() {
        return roomid;
    }

    public void setRoomID(int roomid) {
        this.roomid = roomid;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Connection conn = new Connect().getConn();

    public List<Booking> getByCustomerId(Integer id) {
        String sql = "SELECT * FROM booking WHERE customerid = ? ORDER BY checkindate ASC";
        List<Booking> bookings = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            int rowCount = 0;
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("roomid"),
                        rs.getDate("bookingdate").toLocalDate(),
                        rs.getInt("roomprice"),
//                        rs.getDate("expectedcheckindate").toLocalDate(),
//                        rs.getDate("expectedcheckoutdate").toLocalDate(),
                        rs.getDate("checkindate").toLocalDate(),
                        rs.getDate("checkoutdate").toLocalDate(),
                        rs.getString("status")
                );
                bookings.add(booking);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log full exception stack trace
            throw new RuntimeException(e);
        }
        return bookings;
    }



    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", customerid=" + customerid +
                ", roomid=" + roomid +
                ", bookingDate=" + bookingDate +
                ", roomPrice=" + roomPrice +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", status='" + status +
                '}';
    }
}