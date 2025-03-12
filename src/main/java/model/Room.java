package model;

import connect.Connect;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Room {
    private int roomID;

    private int roomNumber;

    private String roomType;

    private BigDecimal price;

    private String status;

    private int capacity;

    // JavaFX properties
    private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty typeProperty = new SimpleStringProperty();


    // Getters and setters...

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    public StringProperty typeProperty() {
        return typeProperty;
    }



    static Connection conn = new Connect().getConn();

    public static double getTotalRoom() {
        double total = 0;
        String query = "SELECT * FROM Room";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("RoomID");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Method to fetch data for today
    public static double getAvailableRoom() {
        double total = 0;
        String query = "SELECT * FROM Room where Status = 'Available'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("RoomID");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Method to fetch data for today
    public static double getOccupiedRoom() {
        double total = 0;
        String query = "SELECT * FROM Room where Status = 'Occupied'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("RoomID");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Method to fetch data for today
    public static double getMaintenanceRoom() {
        double total = 0;
        String query = "SELECT * FROM Room where Status = 'Maintenance'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("RoomID");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}