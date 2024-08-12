package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.repository.DepartmentRepo;
import com.nk2.crudapp.repository.EmployeeRepo;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepo mockEmployeeRepo;

    @Mock
    private DepartmentRepo mockDepartmentRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;
    private Department department;

    private Employee getEmployeeWithInvalidDepartment() {
        department = Department.builder().id(3).build();
        return Employee.builder().id(1).nameFirst("Nishant").nameLast("Kumar").departments(new HashSet<>(Collections.singletonList(department))).build();
    }

    private Employee getEmployeeWithoutDepartment() {
        return Employee.builder().id(1).nameFirst("Nishant").nameLast("Kumar").build();
    }

    private Employee getEmployeeWithValidDepartment() {
        return Employee.builder().id(1).nameFirst("Nishant").nameLast("Kumar").departments(new HashSet<>(Collections.singletonList(department))).build();
    }

    private Department getMandatoryDepartment() {
        return Department.builder().id(1).name("Organisation").mandatory(true).readOnly(true).build();
    }

    private Employee getSecondaryEmployee(String nameFirst, String nameLast) {
        return Employee.builder().id(1).nameFirst(nameFirst).nameLast(nameLast).departments(new HashSet<>(Collections.singletonList(department))).build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        department = getMandatoryDepartment();
        employee = getEmployeeWithValidDepartment();
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testNewEmployeeSaveSuccess() {
        when(mockDepartmentRepo.findAllById(anyList())).thenReturn(Collections.singletonList(department));
        when(mockDepartmentRepo.getDepartmentsByMandatoryIsTrue()).thenReturn(Collections.singletonList(department));
        when(mockEmployeeRepo.save(any(Employee.class))).thenReturn(employee);

        Employee responseEmployee = employeeServiceImpl.save(employee);
        assertEquals(responseEmployee.getNameFirst(), employee.getNameFirst());
        assertEquals(responseEmployee.getNameLast(), employee.getNameLast());
    }

    @Test
    void testNewEmployeeSaveSuccessWithoutDepartments() {
        when(mockDepartmentRepo.getDepartmentsByMandatoryIsTrue()).thenReturn(Collections.singletonList(department));
        when(mockEmployeeRepo.save(any(Employee.class))).thenReturn(employee);

        Employee responseEmployee = employeeServiceImpl.save(getEmployeeWithoutDepartment());
        assertEquals(responseEmployee.getNameFirst(), employee.getNameFirst());
        assertEquals(responseEmployee.getNameLast(), employee.getNameLast());
    }

    @Test
    void testNewEmployeeSaveFailWithInvalidDepartments() {
        when(mockDepartmentRepo.findAllById(anyList())).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class, () -> employeeServiceImpl.save(getEmployeeWithInvalidDepartment()));
    }

    @Test
    void testEmployeeUpdateSuccess() throws BadRequestException {
        Employee changedEmployee = getSecondaryEmployee("Raj", "Singh");
        when(mockEmployeeRepo.findById(any())).thenReturn(Optional.ofNullable(employee));
        when(mockEmployeeRepo.save(any(Employee.class))).thenReturn(changedEmployee);

        Employee responseEmployee = employeeServiceImpl.update(changedEmployee);
        assertEquals(responseEmployee.getNameFirst(), changedEmployee.getNameFirst());
        assertEquals(responseEmployee.getNameLast(), changedEmployee.getNameLast());
    }

    @Test
    void testEmployeeUpdateFailWithInvalidEmployeeId() {

    }

    @Test
    void testEmployeeUpdateFailWithInvalidDepartmentId() {

    }

    @Test
    void testEmployeeRetrieveSuccess() {

    }

    @Test
    void testEmployeeRetrieveFailWithInvalidEmployeeId() {

    }

    @Test
    void testEmployeeDeleteSuccess() {

    }

    @Test
    void testEmployeeDeleteFailWithInvalidEmployeeId() {

    }

    @Test
    void testGetAllEmployeeSuccess() {
    }

    @Test
    void testGetAllEmployeeFailWithNoContent() {

    }

    @Test
    void testGetEmployeeByIdSuccess() {
    }

    @Test
    void testGetEmployeeByIdFailWithInvalidId() {

    }
}