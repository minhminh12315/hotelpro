package model;

import java.util.Date;

public class BookingUsage {
    private int bookingUsageID; // Corresponds to BookingUsageID in the table
    private int bookingID; // Corresponds to BookingID in the table
    private Integer serviceID; // Corresponds to ServiceID in the table
    private Integer productID; // Corresponds to ProductID in the table
    private int serviceUsagePrice; // Corresponds to ServiceUsagePrice in the table
    private int quantity; // Corresponds to Quantity in the table
    private Date usageDate; // Corresponds to UsageDate in the table

    // Getters and setters...

    public int getBookingUsageID() {
        return bookingUsageID;
    }

    public void setBookingUsageID(int bookingUsageID) {
        this.bookingUsageID = bookingUsageID;
    }

    public int getBookingID() {
        return bookingID;
    }

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

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }
}