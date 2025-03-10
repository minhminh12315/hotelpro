package service;

import dao.BookingUsageDao;
import model.BookingUsage;

import java.util.List;

public class ServiceUsageService {
    private BookingUsageDao serviceUsageDao;

    public ServiceUsageService() {
        this.serviceUsageDao = new BookingUsageDao();
    }

    public void addServiceUsage(BookingUsage serviceUsage) {
        serviceUsageDao.save(serviceUsage);
    }

    public void updateServiceUsage(BookingUsage serviceUsage) {
        serviceUsageDao.update(serviceUsage);
    }

    public void deleteServiceUsage(BookingUsage serviceUsage) {
        serviceUsageDao.delete(serviceUsage);
    }

    public BookingUsage getServiceUsageById(int id) {
        return serviceUsageDao.findById(id);
    }

    public List<BookingUsage> getAllServiceUsages() {
        return serviceUsageDao.getAll();
    }
}