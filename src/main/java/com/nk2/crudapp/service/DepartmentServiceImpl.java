package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements OperationService<Department> {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(Department department) throws BadRequestException {
        Department dbDepartment = getById(department.getId());
        if (department.isReadOnly() && dbDepartment.isReadOnly()) {
            throw new BadRequestException("Department is read-only. Can't update.");
        }

        return departmentRepository.save(department);
    }

    @Override
    public void deleteById(Integer id) throws BadRequestException {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Department id is not available"));

        if (department.isReadOnly()) {
            throw new BadRequestException("Department is read-only. Can't delete.");
        }

        Set<Employee> employees = department.getEmployees();
        for (Employee employee : employees) {
            employee.getDepartments().remove(department);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            throw new NoSuchElementException("Department list is empty.");
        }
        return departments;
    }

    @Override
    public Department getById(Integer id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Department is not available"));
    }
}
