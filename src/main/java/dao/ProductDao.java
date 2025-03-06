package dao;

import connect.Connect;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements BaseDao<Product> {
    @Override
    public void save(Product product) {
        String sql = "INSERT INTO Product (productName, unitPrice, description, unit) VALUES (?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setBigDecimal(2, product.getUnitPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getUnit());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE Product SET productName = ?, unitPrice = ?, description = ?, unit = ? WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setBigDecimal(2, product.getUnitPrice());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getUnit());
            statement.setInt(5, product.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM Product WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getProductID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM Product WHERE productID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productID"));
                product.setProductName(resultSet.getString("productName"));
                product.setUnitPrice(resultSet.getBigDecimal("unitPrice"));
                product.setDescription(resultSet.getString("description"));
                product.setUnit(resultSet.getString("unit"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productID"));
                product.setProductName(resultSet.getString("productName"));
                product.setUnitPrice(resultSet.getBigDecimal("unitPrice"));
                product.setDescription(resultSet.getString("description"));
                product.setUnit(resultSet.getString("unit"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}