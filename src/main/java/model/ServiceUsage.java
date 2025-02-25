package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ServiceUsage")
public class ServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceUsageID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bookingID")
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "serviceID")
    private Service service;

    private int serviceUsagePrice;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private int quantity;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date usageDate;

    // Getters and setters...

    public int getServiceUsageID() {
        return serviceUsageID;
    }

    public void setServiceUsageID(int serviceUsageID) {
        this.serviceUsageID = serviceUsageID;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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
