package dao;

import connect.Connect;
import model.Invoice;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvoiceDao implements BaseDao<Invoice> {


    public double getTotalRevenue() {
        return 0;
    }

    @Override
    public void save(Invoice invoice) {
        String sql = "INSERT INTO invoice (bookingid, issuedate, totalamount, paymentmethod, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Connect.connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoice.getBooking().getBookingID());
            // localdate
            ps.setDate(2, java.sql.Date.valueOf(invoice.getIssueDate()));
            ps.setBigDecimal(3, invoice.getTotalAmount());
            ps.setString(4, invoice.getPaymentMethod());
            ps.setString(5, invoice.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Invoice invoice) {

    }

    @Override
    public void delete(Invoice invoice) {

    }

    @Override
    public Invoice findById(int id) {
        return null;
    }

    @Override
    public List<Invoice> getAll() {
        return List.of();
    }
}