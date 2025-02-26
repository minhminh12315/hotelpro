package dao;

import model.Invoice;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class InvoiceDao implements BaseDao<Invoice> {


    public double getTotalRevenue() {
        return 0;
    }

    @Override
    public void save(Invoice invoice) {

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