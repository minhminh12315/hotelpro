package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Booking {
    private int bookingID;

    private Customer customerid;

    private Room roomid;

    private Date bookingDate;

    private int roomPrice;

    private Date checkInDate;

    private Date checkOutDate;

    private String status;

    public Booking(int bookingid, int customerid, int roomid, LocalDate bookingdate, BigDecimal roomprice, LocalDate expectedcheckindate, LocalDate expectedcheckoutdate, LocalDate localDate, LocalDate localDate1, String status) {
    }

    // Getters and setters...

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Customer getCustomer() {
        return customerid;
    }

    public void setCustomer(Customer customerid) {
        this.customerid = customerid;
    }

    public Room getRoom() {
        return roomid;
    }

    public void setRoom(Room roomid) {
        this.roomid = roomid;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}