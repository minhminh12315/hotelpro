package model;
import connect.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingUsage {
    private int bookingUsageID; // Corresponds to BookingUsageID in the table
    private int bookingID; // Corresponds to BookingID in the table
    private Integer serviceID; // Corresponds to ServiceID in the table
    private Integer productID; // Corresponds to ProductID in the table
    private int serviceUsagePrice; // Corresponds to ServiceUsagePrice in the table
    private int quantity; // Corresponds to Quantity in the table
    private LocalDate usageDate; // Corresponds to UsageDate in the table

    // Additional fields for dashboard display
    private String ServiceTotal;
    private String ProductTotal;
    private String RoomTotal;
    private String TotalRevenueBooking;
    private int usageCount;
    private String serviceName;

    static Connection conn = new Connect().getConn();

    // Default constructor
    public BookingUsage() {
    }

    // Constructor for revenue by source
    public BookingUsage(int bookingID, String roomTotal, String serviceTotal, String productTotal, String totalRevenueBooking) {
        this.bookingID = bookingID;
        this.RoomTotal = roomTotal;
        this.ServiceTotal = serviceTotal;
        this.ProductTotal = productTotal;
        this.TotalRevenueBooking = totalRevenueBooking;
    }

    // Constructor for service usage
    public BookingUsage(String serviceName, int usageCount) {
        this.serviceName = serviceName;
        this.usageCount = usageCount;
    }

    // Getters and setters...
    public int getBookingUsageID() {
        return bookingUsageID;
    }

    public void setBookingUsageID(int bookingUsageID) {
        this.bookingUsageID = bookingUsageID;
    }

//    public int getBookingID() {
//        return bookingID;
//    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
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

    // Getters for dashboard display fields
    public Integer getBookingID() {
        return bookingID;
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

    public int getUsageCount() {
        return usageCount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getTotalRevenueBooking() {
        return TotalRevenueBooking;
    }

    public void setTotalRevenueBooking(String totalRevenueBooking) {
        this.TotalRevenueBooking = totalRevenueBooking;
    }

    // Method to fetch data for today
    public static double getRevenueForToday() {
        double totalRevenue = 0;
        String query = "SELECT * FROM BookingUsage WHERE UsageDate >= CURRENT_DATE AND UsageDate < CURRENT_DATE + INTERVAL '1 day'";

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
        String query = "SELECT * FROM BookingUsage WHERE EXTRACT(MONTH FROM UsageDate) = EXTRACT(MONTH FROM CURRENT_DATE) " +
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
        String query = "SELECT * FROM BookingUsage WHERE EXTRACT(YEAR FROM UsageDate) = EXTRACT(YEAR FROM CURRENT_DATE)";

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

    public List<BookingUsage> getAll() {
        String sql = "SELECT " +
                "bu.BookingID as BookingID, " +
                "COALESCE(b.roomprice, 0) AS RoomPrice, " +
                "COALESCE(s.serviceprice, 0 * bu.quantity) AS ServiceTotal, " +
                "COALESCE(p.unitprice, 0 * bu.quantity) AS ProductTotal, " +
                "SUM(COALESCE(b.roomprice, 0) + COALESCE(s.serviceprice, 0) * bu.quantity + COALESCE(p.unitprice, 0) * bu.quantity) AS RoomTotal " +
                "FROM BookingUsage bu " +
                "LEFT JOIN Service s ON bu.ServiceID = s.ServiceID " +
                "LEFT JOIN Product p ON bu.ProductID = p.ProductID " +
                "LEFT JOIN Booking b ON bu.BookingID = b.BookingID " +
                "GROUP BY bu.BookingID, b.roomprice, s.serviceprice, p.unitprice, b.roomprice, bu.quantity " +
                "ORDER BY RoomPrice DESC";
        List<BookingUsage> bookingUsages = new ArrayList<>();

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                BookingUsage bookingUsage = new BookingUsage(
                        rs.getInt("BookingID"),
                        rs.getString("RoomPrice"),
                        rs.getString("ServiceTotal"),
                        rs.getString("ProductTotal"),
                        rs.getString("RoomTotal")
                );
                bookingUsages.add(bookingUsage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingUsages;
    }

    public static List<BookingUsage> getMostUsedServices() {
        List<BookingUsage> services = new ArrayList<>();
        String query = "SELECT bu.serviceid, s.servicename, bu.quantity as usageCount " +
                "FROM BookingUsage as bu " +
                "JOIN service as s on s.serviceid = bu.serviceid " +
                "WHERE bu.serviceid IS NOT NULL " +
                "GROUP BY bu.serviceid,s.servicename,bu.quantity " +
                "ORDER BY usageCount DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookingUsage bookingUsage = new BookingUsage(
                        rs.getString("servicename"),
                        rs.getInt("usageCount")
                );
                services.add(bookingUsage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public String toString() {
        return "BookingUsage{" +
                "BookingID=" + bookingID +
                ", ServiceTotal=" + ServiceTotal +
                ", ProductTotal=" + ProductTotal +
                ", RoomTotal=" + RoomTotal + "\n" +
                '}';
    }
}