package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepo;
import com.nk2.crudapp.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService implements OperationService<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Employee add(Employee obj) {
        Set<Department> fetchedDepartments = validateDepartmentsExistence(obj.getDepartmentSet());
        addDepartments(obj, fetchedDepartments);
        return employeeRepo.save(obj);
    }

    @Override
    public Employee update(Employee obj) {
        return employeeRepo.save(obj);
    }

    @Override
    public void deleteById(int id) {
        Optional<Employee> emp = employeeRepo.findById(id);
        if(emp.isEmpty()) {
            return;
        }

        Set<Department> departmentList = emp.get().getDepartmentSet();
        departmentList.remove(emp.get());

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

    private void addDepartments(Employee obj, Set<Department> fetchedDepartments) {
        Set<Department> mandatoryDepartmentList = departmentRepo.getDepartmentsByMandatoryIsTrue();
        mandatoryDepartmentList.addAll(fetchedDepartments);
        obj.setDepartmentSet(mandatoryDepartmentList);
    }

    private Set<Department> validateDepartmentsExistence(Set<Department> departments) {
        if(departments == null) {
            return null;
        }
        List<Integer> departmentIds = departments.stream().map(Department::getId).toList();
        List<Department> fetchedDepartments = departmentRepo.findAllById(departmentIds);
        if(fetchedDepartments.isEmpty() || fetchedDepartments.size() != departments.size()) {
            System.out.println("Department not found");
            return null;
        }
        return new HashSet<>(fetchedDepartments);
    }
}
