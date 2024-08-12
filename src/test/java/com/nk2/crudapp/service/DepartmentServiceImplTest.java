package com.nk2.crudapp.service;

import com.nk2.crudapp.entity.Department;
import com.nk2.crudapp.repository.DepartmentRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository mockDepartmentRepository;

    @InjectMocks
    private DepartmentServiceImpl concreteRef;
    private OperationService<Department> ref;

    public static final Integer ID_NEW_DEPARTMENT = 10;
    public static final String NAME_NEW_DEPARTMENT = "Development";

    public static final Integer ID_EXISTING_DEPARTMENT = 20;
    public static final String NAME_EXISTING_DEPARTMENT = "Peoples";

    public static final Integer ID_EXISTING_DEPARTMENT_READONLY_MANDATORY = 25;
    public static final String NAME_EXISTING_DEPARTMENT_READONLY_MANDATORY = "Peoples";

    public static final Integer ID_EXISTING_DEPARTMENT_READONLY = 30;
    public static final String NAME_EXISTING_DEPARTMENT_READONLY = "Peoples";

    private Department newDepartment;
    private Department existingDepartment;
    private Department invalidDepartment;
    private Department existingDepartment_readOnly_mandatory;
    private Department existingDepartment_readOnly;

    @BeforeEach
    void setUp() {
        newDepartment = Department.builder().id(ID_NEW_DEPARTMENT).name(NAME_NEW_DEPARTMENT).mandatory(false).readOnly(false).build();
        existingDepartment = Department.builder().id(ID_EXISTING_DEPARTMENT).name(NAME_EXISTING_DEPARTMENT).mandatory(false).readOnly(false).build();
        invalidDepartment = Department.builder().id(EmployeeServiceImplTest.ID_INVALID_DEPARTMENT).name(EmployeeServiceImplTest.NAME_INVALID_DEPARTMENT).mandatory(false).readOnly(false).build();
        existingDepartment_readOnly_mandatory = Department.builder().id(ID_EXISTING_DEPARTMENT_READONLY_MANDATORY).name(NAME_EXISTING_DEPARTMENT_READONLY_MANDATORY).mandatory(true).readOnly(true).build();
        existingDepartment_readOnly = Department.builder().id(ID_EXISTING_DEPARTMENT_READONLY).name(NAME_EXISTING_DEPARTMENT_READONLY).mandatory(false).readOnly(true).build();

        lenient().when(mockDepartmentRepository.save(newDepartment)).thenReturn(newDepartment);
        lenient().when(mockDepartmentRepository.findById(ID_NEW_DEPARTMENT)).thenReturn(Optional.empty());
        lenient().when(mockDepartmentRepository.findById(ID_EXISTING_DEPARTMENT_READONLY)).thenReturn(Optional.ofNullable(existingDepartment_readOnly));
        lenient().when(mockDepartmentRepository.findById(ID_EXISTING_DEPARTMENT_READONLY_MANDATORY)).thenReturn(Optional.ofNullable(existingDepartment_readOnly_mandatory));
        lenient().when(mockDepartmentRepository.findById(ID_EXISTING_DEPARTMENT)).thenReturn(Optional.ofNullable(existingDepartment));
        lenient().when(mockDepartmentRepository.save(existingDepartment)).thenReturn(existingDepartment);
    }

    @AfterEach
    void tearDown() {
        newDepartment = null;
        existingDepartment = null;
        existingDepartment_readOnly_mandatory = null;
        existingDepartment_readOnly = null;
        invalidDepartment = null;
    }

    @Test
    void testSave_departmentRequested_success() {
        Department actual = concreteRef.save(newDepartment);
        assertEquals(newDepartment, actual);
    }

    @Test
    void testUpdate_departmentRequested_success() throws BadRequestException {
        Department actual = concreteRef.update(existingDepartment);
        assertEquals(NAME_EXISTING_DEPARTMENT, actual.getName());
    }

    @Test
    void testUpdate_departmentRequested_readOnlyDepartment_ThrowsException() {
        assertThrows(BadRequestException.class, () -> concreteRef.update(existingDepartment_readOnly));
    }

    @Test
    void testDeleteById_success() throws BadRequestException {
        concreteRef.deleteById(ID_EXISTING_DEPARTMENT);
    }

    @Test
    void testDeleteById_readOnlyDepartment_ThrowsException() {
        assertThrows(BadRequestException.class, () -> concreteRef.deleteById(ID_EXISTING_DEPARTMENT_READONLY));
    }

    @Test
    void testDeleteById_readOnly_mandatoryDepartment_ThrowsException() {
        assertThrows(BadRequestException.class, () -> concreteRef.deleteById(ID_EXISTING_DEPARTMENT_READONLY_MANDATORY));
    }

    @Test
    void testGetAll_departmentListRequested_success() {
        when(mockDepartmentRepository.findAll()).thenReturn(List.of(existingDepartment));
        List<Department> actual = concreteRef.getAll();
        assertEquals(List.of(existingDepartment), actual);
    }

    @Test
    void testGetAll_departmentListRequested_noData_ThrowsException() {
        when(mockDepartmentRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class, () -> concreteRef.getAll());
    }

    @Test
    void testGetById_departmentRequested_success() {
        Department actual = concreteRef.getById(ID_EXISTING_DEPARTMENT);
        assertEquals(existingDepartment, actual);
    }

    @Test
    void testGetById_departmentRequested_invalidDepartment_ThrowsException() {
        assertThrows(NoSuchElementException.class, ()-> concreteRef.getById(EmployeeServiceImplTest.ID_INVALID_DEPARTMENT));
    }
}