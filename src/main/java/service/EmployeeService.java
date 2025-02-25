package service;

import dao.EmployeeDao;
import model.Employee;

import java.util.List;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public EmployeeService() {
        this.employeeDao = new EmployeeDao();
    }

    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeDao.delete(employee);
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }
}