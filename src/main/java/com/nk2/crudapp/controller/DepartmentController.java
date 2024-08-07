package com.nk2.crudapp.controller;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentRepo departmentRepo;

    @GetMapping("/api/department/getall")
    public ResponseEntity<List<Department>> getAllDepartments() {
        try{
            List<Department> departments = departmentRepo.findAll();
            if(departments.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/department/getbyid/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        try {
            Optional<Department> optDept = departmentRepo.findById(id);
            if(optDept.isPresent()){
                return new ResponseEntity<>(optDept.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/department/add")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        try {
            Department dept = departmentRepo.save(department);
            return new ResponseEntity<>(dept, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/department/update/{id}")
    public ResponseEntity<Department> updateDepartmentById(@PathVariable int id, @RequestBody Department department) {
        try {
            Optional<Department> oldDept = departmentRepo.findById(id);
            if(oldDept.isPresent()) {
                Department dept = oldDept.get();
                dept.setName(department.getName());
                dept.setMandatory(department.isMandatory());
                dept.setReadOnly(department.isReadOnly());
                Department newSavedDepartment = departmentRepo.save(dept);
                return new ResponseEntity<>(newSavedDepartment, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/department/delete/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable int id) {
        try {
            departmentRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
