package dao;

import connect.Connect;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceDao implements BaseDao<Service> {

    @Override
    public void save(Service service) {
        String sql = "INSERT INTO service (serviceName, servicePrice, serviceType, description) VALUES (?, ?, ?, ?)";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, service.getServiceName());
            statement.setBigDecimal(2, service.getServicePrice());
            statement.setString(3, service.getServiceType());
            statement.setString(4, service.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    @Override
    public void update(Service service) {
        // ...existing code...
    }

    @Override
    public void delete(Service service) {
        // ...existing code...
    }

    @Override
    public Service findById(int id) {
        return null;
    }

    @Override
    public List<Service> getAll() {
        return List.of();
    }
}
