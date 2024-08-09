package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DepartmentService implements OperationService<Department> {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Department save(Department obj) {
        return departmentRepo.save(obj);
    }

    @Override
    public Department update(Department obj) throws BadRequestException {
        Department dept = getById(obj.getId());
        if(dept.isReadOnly() && obj.isReadOnly())
        {
            throw new BadRequestException("Department is read-only. Can't update.");
        }

        return departmentRepo.save(obj);
    }

    @Override
    public void deleteById(Integer id) throws BadRequestException {
        Department department = departmentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Department id is not available"));

        if(department.isReadOnly())
        {
            throw new BadRequestException("Department is read-only. Can't delete.");
        }

        Set<Employee> employeeList = department.getEmployees();
        for (Employee employee : employeeList) {
            employee.getDepartments().remove(department);
        }
        departmentRepo.deleteById(id);
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = departmentRepo.findAll();
        if(departments.isEmpty()) {
            throw new NoSuchElementException("Department list is empty.");
        }
        return departments;
    }

    @Override
    public Department getById(Integer id) {
        return departmentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Department is not available"));
    }
}
