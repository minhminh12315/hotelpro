package dao;

import model.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ServiceDao implements BaseDao<Service> {

    @Override
    public void save(Service service) {

    }

    @Override
    public void update(Service service) {

    }

    @Override
    public void delete(Service service) {

    }

    @Override
    public Service findById(int id) {
        return null;
    }

    @Override
    public List<Service> getAll() {
        return List.of();
    }
}