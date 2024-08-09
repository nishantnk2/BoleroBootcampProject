package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepo;
import com.nk2.crudapp.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements OperationService<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Employee save(Employee obj) throws Exception {
        validateDepartmentsExistence(obj);
        saveMandatoryDepartments(obj);
        return employeeRepo.save(obj);
    }

    @Override
    public Employee update(Employee obj) {
        return employeeRepo.save(obj);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        Optional<Employee> emp = employeeRepo.findById(id);
        if(emp.isEmpty()) {
            throw new Exception("Employee id is not available.");
        }
        employeeRepo.deleteById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getById(Integer id) {
        return employeeRepo.findById(id).orElse(null);
    }

    private void saveMandatoryDepartments(Employee obj) {
        obj.getDepartments().addAll(departmentRepo.getDepartmentsByMandatoryIsTrue());
    }

    private void validateDepartmentsExistence(Employee obj) throws Exception {
        if(obj.getDepartments() == null) {
            return;
        }

        List<Integer> departmentIds = obj.getDepartments().stream().map(Department::getId).toList();
        List<Department> fetchedDepartments = departmentRepo.findAllById(departmentIds);
        if(fetchedDepartments.size() != obj.getDepartments().size()) {
            throw new Exception("Department is not available");
        }
    }
}
