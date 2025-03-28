package model;

import connect.Connect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productID;
    private String productName;
    private BigDecimal unitPrice;
    private String description;
    private String unit;
    private int quantity; // Add this field
    private LocalDate lastUpdated; // Add this field

    public Product() {
    }

    public Product(int productID, String productName, BigDecimal unitPrice, String description, String unit) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.description = description;
        this.unit = unit;
    }

    public Product(int productID, String productName, BigDecimal unitPrice, String description, String unit, int quantity, LocalDate lastUpdated) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // Getters and setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    Connection conn = new Connect().getConn();

    public Product getById(Integer id){
        String sql = "SELECT * FROM product WHERE ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getBigDecimal("UnitPrice"),
                        rs.getString("Description"),
                        rs.getString("Unit")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String toString() {
        return ", productName='" + productName +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity;
    }
}