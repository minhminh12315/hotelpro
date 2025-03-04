package service;

import dao.CustomerDao;
import model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;

    public CustomerService() {
        this.customerDao = new CustomerDao();
    }

    public void addCustomer(Customer customer) {
        customerDao.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    public Customer getCustomerById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAll();
    }
}