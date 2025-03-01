package dao;

import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class CustomerDao implements BaseDao<Customer> {

    @Override
    public void save(Customer customer) {


    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public Customer findById(int id) {

        return null;
    }

    @Override
    public List<Customer> getAll() {

        return List.of();
    }
}