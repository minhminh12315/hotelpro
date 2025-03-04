package service;

import dao.ServiceUsageDao;
import model.ServiceUsage;

import java.util.List;

public class ServiceUsageService {
    private ServiceUsageDao serviceUsageDao;

    public ServiceUsageService() {
        this.serviceUsageDao = new ServiceUsageDao();
    }

    public void addServiceUsage(ServiceUsage serviceUsage) {
        serviceUsageDao.save(serviceUsage);
    }

    public void updateServiceUsage(ServiceUsage serviceUsage) {
        serviceUsageDao.update(serviceUsage);
    }

    public void deleteServiceUsage(ServiceUsage serviceUsage) {
        serviceUsageDao.delete(serviceUsage);
    }

    public ServiceUsage getServiceUsageById(int id) {
        return serviceUsageDao.findById(id);
    }

    public List<ServiceUsage> getAllServiceUsages() {
        return serviceUsageDao.getAll();
    }
}