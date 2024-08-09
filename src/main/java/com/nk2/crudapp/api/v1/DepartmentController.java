package com.nk2.crudapp.api.v1;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.service.OperationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private final OperationService<Department> departmentService;

    public DepartmentController(OperationService<Department> departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/getall")
    public List<Department> getAllDepartments() throws CustomException {
        return departmentService.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentService.getById(id);
    }

    @PostMapping("/add")
    public Department saveDepartment(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @PostMapping("/update")
    public Department updateDepartmentById(@RequestBody Department department) throws BadRequestException {
        return departmentService.update(department);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable Integer id) throws BadRequestException {
        departmentService.deleteById(id);
    }
}
