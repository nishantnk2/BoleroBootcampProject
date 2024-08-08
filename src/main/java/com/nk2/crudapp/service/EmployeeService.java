package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements OperationService<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public Employee add(Employee obj) {
        return employeeRepo.save(obj);
    }

    @Override
    public Employee update(Employee obj) {
        return employeeRepo.save(obj);
    }

    @Override
    public void deleteById(int id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employee> getById(int id) {
        return employeeRepo.findById(id);
    }
}
