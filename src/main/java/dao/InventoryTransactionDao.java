package dao;

import model.InventoryTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect.Connect;

public class InventoryTransactionDao implements BaseDao<InventoryTransaction> {

    @Override
    public void save(InventoryTransaction inventoryTransaction) {
        String sql = "INSERT INTO InventoryTransactions (productID, bookingID, employeeID, quantity, transactionType, transactionDate, remarks) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, inventoryTransaction.getProductID());
            // If bookingID is null, set it to SQL NULL
            if (inventoryTransaction.getBookingID() != null) {
                statement.setInt(2, inventoryTransaction.getBookingID());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            // If employeeID is null, set it to SQL NULL
            if (inventoryTransaction.getEmployeeID() != null) {
                statement.setInt(3, inventoryTransaction.getEmployeeID());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(4, inventoryTransaction.getQuantity());
            statement.setString(5, inventoryTransaction.getTransactionType());
            statement.setTimestamp(6, inventoryTransaction.getTransactionDate());
            statement.setString(7, inventoryTransaction.getRemarks());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(InventoryTransaction inventoryTransaction) {
        String sql = "UPDATE InventoryTransactions SET productID = ?, bookingID = ?, employeeID = ?, quantity = ?, transactionType = ?, transactionDate = ?, remarks = ? WHERE transactionID = ?";
        try (Connection connection = Connect.connection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventoryTransaction.getProductID());
            if (inventoryTransaction.getBookingID() != null) {
                statement.setInt(2, inventoryTransaction.getBookingID());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            if (inventoryTransaction.getEmployeeID() != null) {
                statement.setInt(3, inventoryTransaction.getEmployeeID());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(4, inventoryTransaction.getQuantity());
            statement.setString(5, inventoryTransaction.getTransactionType());
            statement.setTimestamp(6, inventoryTransaction.getTransactionDate());
            statement.setString(7, inventoryTransaction.getRemarks());
            statement.setInt(8, inventoryTransaction.getTransactionID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(InventoryTransaction inventoryTransaction) {
        String sql = "DELETE FROM InventoryTransactions WHERE transactionID = ?";
        try (Connection connection = Connect.connection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventoryTransaction.getTransactionID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InventoryTransaction findById(int id) {
        String sql = "SELECT * FROM InventoryTransactions WHERE transactionID = ?";
        try (Connection connection = Connect.connection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToInventoryTransaction(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<InventoryTransaction> getAll() {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM InventoryTransactions";
        try (Connection connection = Connect.connection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                transactions.add(mapResultSetToInventoryTransaction(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    // Helper method to map a ResultSet to an InventoryTransaction object
    private InventoryTransaction mapResultSetToInventoryTransaction(ResultSet resultSet) throws SQLException {
        InventoryTransaction inventoryTransaction = new InventoryTransaction();
        inventoryTransaction.setTransactionID(resultSet.getInt("transactionID"));
        inventoryTransaction.setProductID(resultSet.getInt("productID"));
        inventoryTransaction
                .setBookingID(resultSet.getObject("bookingID") != null ? resultSet.getInt("bookingID") : null);
        inventoryTransaction
                .setEmployeeID(resultSet.getObject("employeeID") != null ? resultSet.getInt("employeeID") : null);
        inventoryTransaction.setQuantity(resultSet.getInt("quantity"));
        inventoryTransaction.setTransactionType(resultSet.getString("transactionType"));
        inventoryTransaction.setTransactionDate(resultSet.getTimestamp("transactionDate"));
        inventoryTransaction.setRemarks(resultSet.getString("remarks"));
        return inventoryTransaction;
    }
}
