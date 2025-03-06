package dao;

import model.Employee;

import java.util.List;

public class EmployeeDao implements BaseDao<Employee> {

    @Override
    public void save(Employee employee) {

    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void delete(Employee employee) {

    }

    @Override
    public Employee findById(int id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return List.of();
    }
}