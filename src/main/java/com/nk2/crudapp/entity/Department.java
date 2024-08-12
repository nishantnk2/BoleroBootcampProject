package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "DEPARTMENT")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTMENT_ID")
    @SequenceGenerator(name = "SEQ_DEPARTMENT_ID", sequenceName = "SEQ_DEPARTMENT_ID", allocationSize = 1)
    private Integer id;

    @NotNull(message = "DEPARTMENT NAME cannot be null")
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull(message = "MANDATORY field cannot be null")
    @Column(name = "MANDATORY", nullable = false)
    private boolean mandatory;

    @NotNull(message = "READONLY field cannot be null")
    @Column(name = "READ_ONLY", nullable = false)
    private boolean readOnly;

    @ManyToMany(mappedBy = "departments")
    private Set<Employee> employees;
}
