package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.entity.Employee;
import com.nk2.crudapp.exception.CustomException;
import com.nk2.crudapp.repository.DepartmentRepository;
import com.nk2.crudapp.repository.EmployeeRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Mock
    private DepartmentRepository mockDepartmentRepository;

    @InjectMocks
    private EmployeeServiceImpl concreteRef;
    private OperationService<Employee> ref;

    public static final Integer ID_NEW_EMPLOYEE = 10;
    public static final String NAME_FIRST_NEW_EMPLOYEE = "Chris";
    public static final String NAME_LAST_NEW_EMPLOYEE = "Morris";

    public static final Integer ID_EXISTING_EMPLOYEE = 12;
    public static final String NAME_FIRST_EXISTING_EMPLOYEE = "Ricky";
    public static final String NAME_LAST_EXISTING_EMPLOYEE = "Martin";

    public static final Integer ID_INVALID_EMPLOYEE = 13;
    public static final String NAME_FIRST_INVALID_EMPLOYEE = "Matt";
    public static final String NAME_LAST_INVALID_EMPLOYEE = "Demon";

    public static final Integer ID_VALID_DEPARTMENT_READONLY_MANDATORY = 1;
    public static final String NAME_VALID_DEPARTMENT_READONLY_MANDATORY = "Organisation";


    public static final Integer ID_VALID_DEPARTMENT_READONLY = 2;
    public static final String NAME_VALID_DEPARTMENT_READONLY = "Development";

    public static final Integer ID_VALID_DEPARTMENT_MANDATORY = 3;
    public static final String NAME_VALID_DEPARTMENT_MANDATORY = "Peoples";

    public static final Integer ID_VALID_DEPARTMENT = 5;
    public static final String NAME_VALID_DEPARTMENT = "QA";

    public static final Integer ID_INVALID_DEPARTMENT = 100;
    public static final String NAME_INVALID_DEPARTMENT = "Operations";

    private Employee newEmployee;
    private Employee existingEmployee;
    private Employee invalidEmployee;

    private Department validDepartment;
    private Department validDepartment_readOnly_mandatory;
    private Department validDepartment_readOnly;
    private Department validDepartment_mandatory;
    private Department invalidDepartment;

    @BeforeEach
    void setUp() {
        validDepartment = Department.builder().id(ID_VALID_DEPARTMENT).name(NAME_VALID_DEPARTMENT).build();
        validDepartment_readOnly_mandatory = Department.builder().id(ID_VALID_DEPARTMENT_READONLY_MANDATORY).name(NAME_VALID_DEPARTMENT_READONLY_MANDATORY).readOnly(true).mandatory(true).build();
        validDepartment_readOnly = Department.builder().id(ID_VALID_DEPARTMENT_READONLY).name(NAME_VALID_DEPARTMENT_READONLY).readOnly(true).build();
        validDepartment_mandatory = Department.builder().id(ID_VALID_DEPARTMENT_MANDATORY).name(NAME_VALID_DEPARTMENT_MANDATORY).mandatory(true).build();
        invalidDepartment = Department.builder().id(ID_INVALID_DEPARTMENT).name(NAME_INVALID_DEPARTMENT).build();

        newEmployee = Employee.builder().id(ID_NEW_EMPLOYEE).nameFirst(NAME_FIRST_NEW_EMPLOYEE).nameLast(NAME_LAST_NEW_EMPLOYEE).departments(new HashSet<>()).build();
        existingEmployee = Employee.builder().id(ID_EXISTING_EMPLOYEE).nameFirst(NAME_FIRST_EXISTING_EMPLOYEE).nameLast(NAME_LAST_EXISTING_EMPLOYEE).departments(new HashSet<>()).build();
        invalidEmployee = Employee.builder().id(ID_INVALID_EMPLOYEE).nameFirst(NAME_FIRST_INVALID_EMPLOYEE).nameLast(NAME_LAST_INVALID_EMPLOYEE).departments(new HashSet<>()).build();

        lenient().when(mockEmployeeRepository.findById(ID_NEW_EMPLOYEE)).thenReturn(Optional.empty());
        lenient().when(mockEmployeeRepository.findById(ID_EXISTING_EMPLOYEE)).thenReturn(Optional.of(existingEmployee));
        lenient().when(mockDepartmentRepository.findAllById(List.of(ID_VALID_DEPARTMENT_READONLY_MANDATORY))).thenReturn(List.of(validDepartment_readOnly_mandatory));
        lenient().when(mockDepartmentRepository.findAllById(List.of(ID_VALID_DEPARTMENT_READONLY))).thenReturn(List.of(validDepartment_readOnly));
        lenient().when(mockDepartmentRepository.findAllById(List.of(ID_VALID_DEPARTMENT))).thenReturn(List.of(validDepartment));
        lenient().when(mockDepartmentRepository.findAllById(List.of(ID_VALID_DEPARTMENT_MANDATORY))).thenReturn(List.of(validDepartment_mandatory));
        lenient().when(mockDepartmentRepository.getDepartmentsByMandatoryIsTrue()).thenReturn(List.of(validDepartment_readOnly_mandatory));
        lenient().when(mockEmployeeRepository.save(newEmployee)).thenReturn(newEmployee);
        lenient().when(mockEmployeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
        lenient().when(mockEmployeeRepository.findById(ID_INVALID_EMPLOYEE)).thenReturn(Optional.empty());
    }

    @AfterEach
    void tearDown() throws Exception {
        newEmployee = null;
        existingEmployee = null;

        validDepartment = null;
        validDepartment_readOnly = null;
        validDepartment_readOnly_mandatory = null;
        validDepartment_mandatory = null;
        invalidDepartment = null;

        concreteRef = null;
        ref = null;
    }

    @Test
    void testSave_noDepartmentsRequested_successWithMandatoryDepartments() {
        Employee actual = concreteRef.save(newEmployee);
        assertTrue(actual == newEmployee);
        assertEquals(NAME_FIRST_NEW_EMPLOYEE, actual.getNameFirst());
        assertEquals(NAME_LAST_NEW_EMPLOYEE, actual.getNameLast());
    }

    @Test
    void testSave_departmentRequested_successWithValidDepartment() {
        newEmployee.getDepartments().add(validDepartment);
        Employee actual = concreteRef.save(newEmployee);
        assertEquals(NAME_FIRST_NEW_EMPLOYEE, actual.getNameFirst());
        assertEquals(NAME_LAST_NEW_EMPLOYEE, actual.getNameLast());
    }

    @Test
    void testSave_employeeRequested_invalidDepartment_exceptionThrown() {
        newEmployee.getDepartments().add(invalidDepartment);
        assertThrows(NoSuchElementException.class, () -> concreteRef.save(newEmployee));
    }

    @Test
    void testUpdate_employeeRequested_success() throws BadRequestException {
        Employee actual = concreteRef.update(existingEmployee);
        assertEquals(NAME_FIRST_EXISTING_EMPLOYEE, actual.getNameFirst());
        assertEquals(NAME_LAST_EXISTING_EMPLOYEE, actual.getNameLast());
    }

    @Test
    void testUpdate_employeeRequested_invalidEmployee_exceptionThrown() throws BadRequestException {
        assertThrows(NoSuchElementException.class, () -> concreteRef.update(invalidEmployee));
    }

    @Test
    void testUpdate_employeeRequested_invalidDepartment_exceptionThrown() throws BadRequestException {
        existingEmployee.getDepartments().add(invalidDepartment);
        assertThrows(NoSuchElementException.class, () -> concreteRef.save(existingEmployee));
    }

    @Test
    void testGetById_employeeRequested_success() {
        Employee actual = concreteRef.getById(ID_EXISTING_EMPLOYEE);
        assertEquals(NAME_FIRST_EXISTING_EMPLOYEE, actual.getNameFirst());
        assertEquals(NAME_LAST_EXISTING_EMPLOYEE, actual.getNameLast());
    }

    @Test
    void testGetById_employeeRequested_invalidEmployee_exceptionThrown() {
        assertThrows(NoSuchElementException.class, () -> concreteRef.getById(ID_INVALID_EMPLOYEE));
    }

//    @Test
//    void testDelete_success() {
//        concreteRef.deleteById(ID_EXISTING_EMPLOYEE);
//        verify(mockEmployeeRepository.deleteById(ID_EXISTING_EMPLOYEE));
//    }

    @Test
    void testDelete_invalidEmployee_exceptionThrown() {
        assertThrows(NoSuchElementException.class, () -> concreteRef.deleteById(ID_INVALID_EMPLOYEE));
    }

    @Test
    void testGetAll_employeeListRequested_success() throws CustomException {
        when(mockEmployeeRepository.findAll()).thenReturn(List.of(existingEmployee));
        List<Employee> actual = concreteRef.getAll();
        assertEquals(List.of(existingEmployee), actual);
    }

    @Test
    void testGetAll_employeeListRequested_noData_exceptionThrown() throws CustomException {
        when(mockEmployeeRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CustomException.class, () -> concreteRef.getAll());
    }
}