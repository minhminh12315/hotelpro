package model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'Staff'")
    private String role;

    @Column(nullable = false, length = 255)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty nameProperty = new SimpleStringProperty();
    private final StringProperty phoneProperty = new SimpleStringProperty();
    private final StringProperty emailProperty = new SimpleStringProperty();
    private final StringProperty roleProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();

    // Getters and setters...

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public IntegerProperty idProperty() {
        return idProperty;
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public StringProperty phoneProperty() {
        return phoneProperty;
    }

    public StringProperty emailProperty() {
        return emailProperty;
    }

    public StringProperty roleProperty() {
        return roleProperty;
    }

    public StringProperty passwordProperty() {
        return passwordProperty;
    }
}
