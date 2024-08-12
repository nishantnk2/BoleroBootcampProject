package com.nk2.crudapp.api.v1;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.service.OperationService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private final OperationService<Department> departmentServiceImpl;

    public DepartmentController(OperationService<Department> departmentServiceImpl) {
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @GetMapping("/getall")
    public List<Department> getAllDepartments() throws CustomException {
        return departmentServiceImpl.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentServiceImpl.getById(id);
    }

    @PostMapping("/add")
    public Department saveDepartment(@Valid @RequestBody Department department) {
        return departmentServiceImpl.save(department);
    }

    @PostMapping("/update")
    public Department updateDepartmentById(@Valid @RequestBody Department department) throws BadRequestException {
        return departmentServiceImpl.update(department);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable Integer id) throws BadRequestException {
        departmentServiceImpl.deleteById(id);
    }
}
