package model;

import connect.Connect;
import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Service {
    private int serviceID;

    private String serviceName;

    private BigDecimal servicePrice;

    private String serviceType;

    private String description;

    // JavaFX properties
    private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty nameProperty = new SimpleStringProperty();


    // Getters and setters...

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public StringProperty nameProperty() {
        return nameProperty;
    }


    static Connection conn = new Connect().getConn();
    public static double getTotalServicesToday(){
        double total = 0;
        String query = "SELECT * FROM ServiceUsage WHERE UsageDate >= CURRENT_DATE AND UsageDate < CURRENT_DATE + INTERVAL '1 day'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("serviceusageid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    public static double getTotalServicesMonthly(){
        double total = 0;
        String query = "SELECT * FROM ServiceUsage WHERE EXTRACT(MONTH FROM UsageDate) = EXTRACT(MONTH FROM CURRENT_DATE)" +
                "AND EXTRACT(YEAR FROM UsageDate) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("serviceusageid");
                total += quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
