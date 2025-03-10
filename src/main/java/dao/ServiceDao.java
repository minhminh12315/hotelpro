package dao;

import connect.Connect;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql = "UPDATE service SET serviceName = ?, servicePrice = ?, serviceType = ?, description = ? WHERE serviceID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getServiceName());
            statement.setBigDecimal(2, service.getServicePrice());
            statement.setString(3, service.getServiceType());
            statement.setString(4, service.getDescription());
            statement.setInt(5, service.getServiceID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Service service) {
        String sql = "DELETE FROM service WHERE serviceID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, service.getServiceID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Service findById(int id) {
        Service service = null;
        String sql = "SELECT * FROM service WHERE serviceID = ?";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    service = new Service();
                    service.setServiceID(resultSet.getInt("serviceID"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    service.setServicePrice(resultSet.getBigDecimal("servicePrice"));
                    service.setServiceType(resultSet.getString("serviceType"));
                    service.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public List<Service> getAll() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM service";
        try (Connection connection = Connect.connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceID(resultSet.getInt("serviceID"));
                service.setServiceName(resultSet.getString("serviceName"));
                service.setServicePrice(resultSet.getBigDecimal("servicePrice"));
                service.setServiceType(resultSet.getString("serviceType"));
                service.setDescription(resultSet.getString("description"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
