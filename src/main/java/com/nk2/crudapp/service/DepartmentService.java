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
    public Department add(Department obj) {
        return departmentRepo.save(obj);
    }

    @Override
    public Department update(Department obj) {
        Optional<Department> dept = departmentRepo.findById(obj.getId());
        if(dept.isEmpty()) {
            System.out.println("Department not found");
            return null;
        }

        if(dept.get().isReadOnly() && obj.isReadOnly())
        {
            System.out.println("Department is read-only. Can't update.");
            return null;
        }

        return departmentRepo.save(obj);
    }

    @Override
    public void deleteById(int id) {
        Optional<Department> dept = departmentRepo.findById(id);
        if(dept.isEmpty()) {
            System.out.println("Department not found");
            return;
        }

        if(dept.get().isReadOnly())
        {
            System.out.println("Department is read-only. Can not delete.");
            return;
        }

        Set<Employee> employeeList = dept.get().getEmployeeSet();
        employeeList.remove(dept.get());
        departmentRepo.deleteById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Optional<Department> getById(int id) {
        return departmentRepo.findById(id);
    }
}
