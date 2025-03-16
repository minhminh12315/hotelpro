package model.additional;
import connect.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceUsage {
    private int serviceUsageID;
    private int booking;
    private int service;
    private int product;
    private int serviceUsagePrice;
    private int quantity;
    private LocalDate usageDate;

    public ServiceUsage() {
    }

//
//    public ServiceUsage(int serviceUsageID, int booking, int service, int product, int serviceUsagePrice, int quantity, LocalDate usageDate) {
//        this.serviceUsageID = serviceUsageID;
//        this.booking = booking;
//        this.service = service;
//        this.product = product;
//        this.serviceUsagePrice = serviceUsagePrice;
//        this.quantity = quantity;
//        this.usageDate = usageDate;
//    }


    // Getters and setters...

    public int getServiceUsageID() {
        return serviceUsageID;
    }

    public void setServiceUsageID(int serviceUsageID) {
        this.serviceUsageID = serviceUsageID;
    }

    public int getBooking() {
        return booking;
    }

    public void setBooking(int booking) {
        this.booking = booking;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getServiceUsagePrice() {
        return serviceUsagePrice;
    }

    public void setServiceUsagePrice(int serviceUsagePrice) {
        this.serviceUsagePrice = serviceUsagePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }



    static Connection conn = new Connect().getConn();

    // Method to fetch data for today
    public static double getRevenueForToday() {
        double totalRevenue = 0;
        String query = "SELECT * FROM ServiceUsage WHERE UsageDate >= CURRENT_DATE AND UsageDate < CURRENT_DATE + INTERVAL '1 day'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int serviceUsagePrice = rs.getInt("ServiceUsagePrice");
                int quantity = rs.getInt("Quantity");
                totalRevenue += serviceUsagePrice * quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    // Method to fetch data for the current month
    public static double getRevenueForMonth() {
        double totalRevenue = 0;
        String query = "SELECT * FROM ServiceUsage WHERE EXTRACT(MONTH FROM UsageDate) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                "AND EXTRACT(YEAR FROM UsageDate) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int serviceUsagePrice = rs.getInt("ServiceUsagePrice");
                int quantity = rs.getInt("Quantity");
                totalRevenue += serviceUsagePrice * quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    // Method to fetch data for the current year
    public static double getRevenueForYear() {
        double totalRevenue = 0;
        String query = "SELECT * FROM ServiceUsage WHERE EXTRACT(YEAR FROM UsageDate) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int serviceUsagePrice = rs.getInt("ServiceUsagePrice");
                int quantity = rs.getInt("Quantity");
                totalRevenue += serviceUsagePrice * quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    private Integer BookingID;
    private String ServiceTotal;
    private String ProductTotal;
    private String RoomTotal;

    public Integer getBookingID() {
        return BookingID;
    }

    public void setBookingID(Integer bookingID) {
        BookingID = bookingID;
    }

    public String getServiceTotal() {
        return ServiceTotal;
    }

    public void setServiceTotal(String serviceTotal) {
        ServiceTotal = serviceTotal;
    }

    public String getProductTotal() {
        return ProductTotal;
    }

    public void setProductTotal(String productTotal) {
        ProductTotal = productTotal;
    }

    public String getRoomTotal() {
        return RoomTotal;
    }

    public void setRoomTotal(String roomTotal) {
        RoomTotal = roomTotal;
    }

    public ServiceUsage(Integer BookingID, String ServiceTotal, String ProductTotal, String RoomTotal) {
        this.BookingID = BookingID;
        this.ServiceTotal = ServiceTotal;
        this.ProductTotal = ProductTotal;
        this.RoomTotal = RoomTotal;
    }

    public List<ServiceUsage> getAll() {
        String sql = "SELECT " +
                "su.BookingID as BookingID, " +
                "SUM(COALESCE(su.ServiceUsagePrice, 0) * su.Quantity) AS ServiceTotal, " +
                "SUM(COALESCE(p.unitprice, 0) * su.Quantity) AS ProductTotal, " +
                "SUM(COALESCE(b.roomprice, 0) + COALESCE(su.ServiceUsagePrice, 0) + COALESCE(p.unitprice, 0)) AS RoomTotal " +
                "FROM ServiceUsage su " +
                "LEFT JOIN Service s ON su.ServiceID = s.ServiceID " +
                "LEFT JOIN Product p ON su.ProductID = p.ProductID " +
                "LEFT JOIN Booking b ON su.BookingID = b.BookingID " +
                "GROUP BY su.BookingID " +
                "ORDER BY su.BookingID ASC";
        List<ServiceUsage> serviceUsages = new ArrayList<>();

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                ServiceUsage serviceUsage = new ServiceUsage(
                        rs.getInt("BookingID"),
                        rs.getString("ServiceTotal"),
                        rs.getString("ProductTotal"),
                        rs.getString("RoomTotal")
                );
                serviceUsages.add(serviceUsage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return serviceUsages;
    }

    private int usageCount;
    private String serviceName;

    public int getUsageCount() {
        return usageCount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceUsage(String servicename, int usageCount) {
        this.serviceName = servicename;
        this.usageCount = usageCount;
    }

    public static List<ServiceUsage> getMostUsedServices() {
        List<ServiceUsage> services = new ArrayList<>();
        String query = "SELECT su.serviceid, s.servicename, su.quantity as usageCount " +
                "FROM ServiceUsage as su " +
                "JOIN service as s on s.serviceid = su.serviceid " +
                "WHERE su.serviceid IS NOT NULL " +
                "GROUP BY su.serviceid,s.servicename,su.quantity " +
                "ORDER BY usageCount DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ServiceUsage serviceUsage = new ServiceUsage(
                        rs.getString("servicename"),
                        rs.getInt("usageCount")
                );
                services.add(serviceUsage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }


    @Override
    public String toString() {
        return "ServiceUsage{" +
                "BookingID=" + BookingID +
                ", ServiceTotal=" + ServiceTotal +
                ", ProductTotal=" + ProductTotal +
                ", RoomTotal=" + RoomTotal + "\n" +
                '}';
    }
}
