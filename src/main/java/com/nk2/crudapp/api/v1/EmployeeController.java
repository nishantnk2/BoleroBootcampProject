package com.nk2.crudapp.api.v1;

import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.service.OperationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@Validated
public class EmployeeController {

    @Autowired
    private final OperationService<Employee> employeeServiceImpl;

    public EmployeeController(OperationService<Employee> employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping("/getall")
    public List<Employee> getAllEmployees() throws CustomException {
        return employeeServiceImpl.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeServiceImpl.getById(id);
    }

    @PostMapping("/add")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeServiceImpl.save(employee);
    }

    @PostMapping("/update")
    public Employee updateEmployee(@RequestBody Employee newEmp) throws BadRequestException {
        return employeeServiceImpl.update(newEmp);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) throws BadRequestException {
        employeeServiceImpl.deleteById(id);
    }
}
