package com.nk2.crudapp.repository;

import com.nk2.crudapp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> getDepartmentsByMandatoryIsTrue();
}
