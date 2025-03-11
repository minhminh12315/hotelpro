package model;

import connect.Connect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryTransaction {
    private int transactionID;
    private int productID;
    private Integer bookingID;
    private Integer employeeID;
    private int quantity;
    private String transactionType;
    private LocalDate transactionDate;
    private String remarks;

    public InventoryTransaction(){

    }

    // Getters and setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    private String productName;

    public String getProductName() {
        return productName;
    }

    public InventoryTransaction(int transactionID, String productName, int quantity, LocalDate transactiondate) {
        this.transactionID = transactionID;
        this.productName = productName;
        this.quantity = quantity;
        this.transactionDate = transactiondate;
    }

    static Connection conn = new Connect().getConn();

    public List<InventoryTransaction> getInventoryTransactions() {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String query = "SELECT it.transactionID, p.productname, it.quantity, it.transactionDate FROM inventorytransactions as it " +
                "join product as p on p.productid = it.productid";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InventoryTransaction transaction = new InventoryTransaction(
                        rs.getInt("transactionID"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDate("transactionDate").toLocalDate()
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}