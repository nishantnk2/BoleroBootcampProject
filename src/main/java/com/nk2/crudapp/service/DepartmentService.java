package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return departmentRepo.save(obj);
    }

    @Override
    public void deleteById(int id) {
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
