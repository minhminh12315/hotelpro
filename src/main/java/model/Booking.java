package model;

import connect.Connect;
import dao.BookingDao;
import dao.CustomerDao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    static Connection conn = new Connect().getConn();

    public List<Booking> getByCustomerId(Integer id) {
        String sql = "SELECT * FROM booking WHERE customerid = ? ORDER BY checkindate ASC";
        List<Booking> bookings = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                java.sql.Date bookingDate = rs.getDate("bookingdate");
                java.sql.Date checkInDate = rs.getDate("checkindate");
                Date checkOutDate = rs.getDate("checkoutdate");

                Booking booking = new Booking(
                        rs.getInt("bookingid"),
                        rs.getInt("customerid"),
                        rs.getInt("roomid"),
                        bookingDate != null ? bookingDate.toLocalDate() : null,
                        rs.getInt("roomprice"),
                        checkInDate != null ? checkInDate.toLocalDate() : null,
                        checkOutDate != null ? checkOutDate.toLocalDate() : null,
                        rs.getString("status")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log full exception stack trace
            throw new RuntimeException(e);
        }
        return bookings;
    }



    public static double getTotalPendingBooking() {
        double total = 0;
        String query = "SELECT * FROM Booking where Status = 'Pending'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("bookingid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public static double getTotalCheckedInBooking() {
        double total = 0;
        String query = "SELECT * FROM Booking where Status = 'CheckedIn'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("bookingid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public static double getTotalCheckedOutBooking() {
        double total = 0;
        String query = "SELECT * FROM Booking where Status = 'CheckedOut'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("bookingid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }


    private String fullname;
    private String roomnumber;

    public String getFullname() {
        return fullname;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public Booking(int bookingid, String fullname, String roomnumber, LocalDate checkInDate) {
        this.bookingID = bookingid;
        this.fullname = fullname;
        this.roomnumber = roomnumber;
        this.checkInDate = checkInDate;
    }

    public List<Booking> getUpcomingBooking() {
        String query = "SELECT b.bookingid, c.fullname, r.roomnumber, b.checkindate " +
                "FROM Booking as b " +
                "join customer as c on c.customerid = b.customerid " +
                "join room as r on r.roomid = b.roomid " +
                "where b.Status = 'CheckedOut'";
        List<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Booking booking = new Booking(
                    rs.getInt("bookingid"),
                    rs.getString("fullname"),
                    rs.getString("roomnumber"),
                    rs.getDate("checkindate").toLocalDate());
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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