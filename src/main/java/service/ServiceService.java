package service;

import dao.ServiceDao;
import model.Service;

import java.util.List;

public class ServiceService {
    private ServiceDao serviceDao;

    public ServiceService() {
        this.serviceDao = new ServiceDao();
    }

    public void addService(Service service) {
        serviceDao.save(service);
    }

    public void updateService(Service service) {
        serviceDao.update(service);
    }

    public void deleteService(Service service) {
        serviceDao.delete(service);
    }

    public Service getServiceById(int id) {
        return serviceDao.findById(id);
    }

    public List<Service> getAllServices() {
        return serviceDao.getAll();
    }
}