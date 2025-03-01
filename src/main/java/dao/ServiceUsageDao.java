package dao;

import model.ServiceUsage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ServiceUsageDao implements BaseDao<ServiceUsage> {

    @Override
    public void save(ServiceUsage serviceUsage) {

    }

    @Override
    public void update(ServiceUsage serviceUsage) {

    }

    @Override
    public void delete(ServiceUsage serviceUsage) {

    }

    @Override
    public ServiceUsage findById(int id) {
        return null;
    }

    @Override
    public List<ServiceUsage> getAll() {
        return List.of();
    }
}