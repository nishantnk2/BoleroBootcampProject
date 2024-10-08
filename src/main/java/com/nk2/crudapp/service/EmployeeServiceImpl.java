package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.repository.DepartmentRepository;
import com.nk2.crudapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements OperationService<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentReposiroty;

    @Override
    public Employee save(Employee employee) {
        validateDepartmentsExistence(employee);
        saveMandatoryDepartments(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) throws BadRequestException {
        if (employee.getId() == null) {
            throw new BadRequestException("Employee id is required");
        }
        employeeRepository.findById(employee.getId()).orElseThrow(() -> new NoSuchElementException("Employee id not found"));
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Employee is not available"));
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAll() throws CustomException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new CustomException("Employee list is empty.");
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Employee is not available"));
    }

    private void saveMandatoryDepartments(Employee employee) {
        employee.getDepartments().addAll(departmentReposiroty.getDepartmentsByMandatoryIsTrue());
    }

    private void validateDepartmentsExistence(Employee employee) {
        if (employee.getDepartments() == null) {
            employee.setDepartments(new HashSet<>());
            return;
        }

        List<Integer> inputDepartmentIds = employee.getDepartments().stream().map(Department::getId).toList();
        List<Department> dbDepartmentIds = departmentReposiroty.findAllById(inputDepartmentIds);
        if (dbDepartmentIds.size() != employee.getDepartments().size()) {
            throw new NoSuchElementException("Department is not available");
        }
    }
}
