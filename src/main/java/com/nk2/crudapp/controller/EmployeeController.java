package com.nk2.crudapp.controller;

import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@Validated
public class EmployeeController {

    @Autowired
    private final OperationService<Employee> employeeService;

    public EmployeeController(OperationService<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getall")
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @PostMapping("/add")
    public Employee saveEmployee(@RequestBody Employee employee) throws Exception {
        return employeeService.save(employee);
    }

    @PostMapping("/update")
    public Employee updateEmployee(@RequestBody Employee newEmp) throws Exception {
        return employeeService.update(newEmp);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) throws Exception {
        employeeService.deleteById(id);
    }

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World";
    }
}
