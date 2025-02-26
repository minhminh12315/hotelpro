package model;

import jakarta.persistence.*;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Room {
    private int roomID;

    private String roomType;

    private BigDecimal price;

    private String status;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    public StringProperty typeProperty() {
        return typeProperty;
    }
}