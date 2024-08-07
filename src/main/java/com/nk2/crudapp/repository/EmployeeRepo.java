package com.nk2.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nk2.crudapp.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
