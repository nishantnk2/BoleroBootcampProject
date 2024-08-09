package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Department update(Department obj) throws Exception {
        Optional<Department> dept = departmentRepo.findById(obj.getId());
        if(dept.isEmpty()) {
            throw new Exception("Department is not available");
        }

        if(dept.get().isReadOnly() && obj.isReadOnly())
        {
            throw new Exception("Department is read-only. Can't update.");
        }

        return departmentRepo.save(obj);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        Optional<Department> dept = departmentRepo.findById(id);
        if(dept.isEmpty()) {
            throw new Exception("Department is not available");
        }
        Department department = departmentRepo.findById(id).get();
        if(department.isReadOnly())
        {
            throw new Exception("Department is read-only. Can't delete.");
        }

        Set<Employee> employeeList = department.getEmployees();
        for (Employee employee : employeeList) {
            employee.getDepartments().remove(department);
        }

        departmentRepo.deleteById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Department getById(Integer id) {
        return departmentRepo.findById(id).orElse(null);
    }
}
