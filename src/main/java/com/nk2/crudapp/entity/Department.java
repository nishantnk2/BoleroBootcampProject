package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "DEPARTMENT")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTMENT_ID")
    @SequenceGenerator(name = "SEQ_DEPARTMENT_ID", sequenceName = "SEQ_DEPARTMENT_ID", allocationSize = 1)
    private int id;

    @NotNull(message = "DEPARTMENT NAME cannot be null")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "MANDATORY field cannot be null")
    @Column(nullable = false)
    private boolean mandatory;

    @NotNull(message = "READONLY field cannot be null")
    @Column(nullable = false)
    private boolean readOnly;

    @ManyToMany(mappedBy = "departments")
    private Set<Employee> employees;
}
