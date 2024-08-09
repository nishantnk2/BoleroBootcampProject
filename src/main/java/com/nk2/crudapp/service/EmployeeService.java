package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.exception.BadRequestException;
import com.nk2.crudapp.exception.ResourceNotFoundException;
import com.nk2.crudapp.repository.DepartmentRepo;
import com.nk2.crudapp.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements OperationService<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Employee save(Employee obj) {
        validateDepartmentsExistence(obj);
        saveMandatoryDepartments(obj);
        return employeeRepo.save(obj);
    }

    @Override
    public Employee update(Employee obj) {
        if(obj.getId() == null)
        {
            throw new BadRequestException("Employee id is required");
        }
        employeeRepo.findById(obj.getId()).orElseThrow(() -> new ResourceNotFoundException("Employee is not available"));
        return employeeRepo.save(obj);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee is not available"));
        employeeRepo.deleteById(id);
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = employeeRepo.findAll();
        if(employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee list is empty.");
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee is not available"));
    }

    private void saveMandatoryDepartments(Employee obj) {
       obj.getDepartments().addAll(departmentRepo.getDepartmentsByMandatoryIsTrue());
    }

    private void validateDepartmentsExistence(Employee obj) {
        if(obj.getDepartments() == null) {
            obj.setDepartments(new HashSet<Department>());
            return;
        }

        List<Integer> departmentIds = obj.getDepartments().stream().map(Department::getId).toList();
        List<Department> fetchedDepartments = departmentRepo.findAllById(departmentIds);
        if(fetchedDepartments.size() != obj.getDepartments().size()) {
            throw new ResourceNotFoundException("Department is not available");
        }
    }
}
