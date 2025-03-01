package model;

import jakarta.persistence.*;
import connect.Connect;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int employeeID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String role;
    private String password;
    private LocalDate startDate;

    public Employee(){

    }

    public Employee(int employeeID, String fullName, String phoneNumber, String email, String role, String password, LocalDate startDate) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.password = password;
        this.startDate = startDate;
    }

    public Employee(String fullName, String phoneNumber, String email, String role, String password, LocalDate startDate) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.password = password;
        this.startDate = startDate;
    }

    public Employee(Integer employeeID, String name, String phone, String email, String role, String password, LocalDate startDate) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.password = password;
        this.startDate = startDate;
    }

    // Getters and setters
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
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    Connection conn = new Connect().getConn();

    public List<Employee> getAll(){
        String sql = "SELECT * FROM employee " +
                "ORDER BY employeeID ASC";
        List<Employee> events = new ArrayList<>();
        try{
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                Employee employee = new Employee(
                        rs.getInt("employeeID"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getDate("startDate").toLocalDate());
                events.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public void delete(int id){
        String sql = "DELETE FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addEmployee(){
        String sql = "INSERT INTO employee(fullName, phoneNumber, email, role, password, startDate) VALUES(?,?,?,?,?,?)";

        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.fullName);
            ps.setString(2, this.phoneNumber);
            ps.setString(3, this.email);
            ps.setString(4, this.role);
            ps.setString(5, this.password);
            ps.setDate(6, Date.valueOf(this.startDate));
            int res = ps.executeUpdate();
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public Employee getById(Integer id){
        String sql = "SELECT * FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Employee(
                        rs.getInt("employeeID"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getDate("startDate").toLocalDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void update(){
        String sql = "UPDATE employee SET fullName = ?, phoneNumber = ?, email = ?, role = ?, password = ?, startDate = ? WHERE employeeID = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, this.fullName);
            ps.setString(2, this.phoneNumber);
            ps.setString(3, this.email);
            ps.setString(4, this.role);
            ps.setString(5, this.password);
            ps.setDate(6, Date.valueOf(this.startDate));
            ps.setInt(7, this.employeeID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
