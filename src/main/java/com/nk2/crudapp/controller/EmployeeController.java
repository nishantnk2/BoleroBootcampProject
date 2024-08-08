package com.nk2.crudapp.controller;

import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.EmployeeRepo;
import com.nk2.crudapp.service.EmployeeService;
import com.nk2.crudapp.service.OperationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private OperationService<Employee> employeeService;

    @GetMapping("/api/employee/getall")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAll();
            if(employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/employee/getbyid/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        try {
            Optional<Employee> employee = employeeService.getById(id);
            if(employee.isPresent()) {
                return new ResponseEntity<>(employee.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/employee/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            Employee returnEmp = employeeService.add(employee);
            return new ResponseEntity<>(returnEmp, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/employee/update/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable int id, @RequestBody Employee newEmp) {
        try {
            Optional<Employee> oldEmp = employeeService.getById(id);
            if(oldEmp.isPresent()) {
                Employee emp = oldEmp.get();
                emp.setFirstName(newEmp.getFirstName());
                emp.setLastName(newEmp.getLastName());
                Employee newSavedEmployee = employeeService.update(emp);
                return new ResponseEntity<>(newSavedEmployee, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/employee/delete/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable int id) {
        try {
            employeeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/helloworld")
    public String helloWorld() {
        return "Hello World";
    }
}
