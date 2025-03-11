package model;

import connect.Connect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int productID;
    private int quantity;
    private LocalDate lastUpdated;

    public Inventory(){

    }

    // Getters and setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    static Connection conn = new Connect().getConn();
    public static double getTotalProduct(){
        double total = 0;
        String query = "SELECT * FROM Inventory";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("productID");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }


    public Inventory(int productID, int quantity, LocalDate lastUpdated) {
        this.productID = productID;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public List<Inventory> getLowStockProducts() {
        List<Inventory> lowStockProducts = new ArrayList<>();
        String query = "SELECT productID, quantity, lastUpdated FROM Inventory WHERE quantity < 40";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventory product = new Inventory(
                        rs.getInt("productID"),
                        rs.getInt("quantity"),
                        rs.getDate("lastUpdated").toLocalDate()
                );
                lowStockProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockProducts;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productID=" + productID +
                ", quantity=" + quantity +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}