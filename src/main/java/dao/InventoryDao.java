package dao;

import model.Inventory;
import model.BookingUsage;

import connect.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class InventoryDao implements BaseDao<Inventory> {
    @Override
    public void save(Inventory inventory) {
        String sql = "INSERT INTO inventory (productID, quantity, lastUpdated) VALUES (?, ?, ?)";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventory.getProductID());
            statement.setInt(2, inventory.getQuantity());
            statement.setObject(3, inventory.getLastUpdated()); // Use LocalDate directly
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Inventory inventory) {
        String sql = "UPDATE inventory SET quantity = ?, lastUpdated = ? WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventory.getQuantity());
            statement.setObject(2, inventory.getLastUpdated()); // Use LocalDate directly
            statement.setInt(3, inventory.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Inventory inventory) {
        String sql = "DELETE FROM inventory WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventory.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory findById(int id) {
        String sql = "SELECT * FROM inventory WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Inventory inventory = new Inventory();
                inventory.setProductID(resultSet.getInt("productID"));
                inventory.setQuantity(resultSet.getInt("quantity"));
                inventory.setLastUpdated(resultSet.getObject("lastUpdated", LocalDate.class));
                return inventory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inventory> getAll() {
        String sql = "SELECT * FROM inventory";
        List<Inventory> inventories = new ArrayList<>();
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Inventory inventory = new Inventory();
                inventory.setProductID(resultSet.getInt("productID"));
                inventory.setQuantity(resultSet.getInt("quantity"));
                inventory.setLastUpdated(resultSet.getObject("lastUpdated", LocalDate.class));
                inventories.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventories;
    }
}
