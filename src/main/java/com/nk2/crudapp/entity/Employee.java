package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE_ID")
    @SequenceGenerator(name = "SEQ_EMPLOYEE_ID", sequenceName = "SEQ_EMPLOYEE_ID", allocationSize = 1)
    private int id;

    @NotNull(message = "FIRSTNAME cannot be null")
    @Column(name = "NAME_FIRST", nullable = false)
    private String firstName;

    @NotNull(message = "LASTNAME cannot be null")
    @Column(name = "NAME_LAST", nullable = false)
    private String lastName;

    @NotNull(message = "DEPARTMENT cannot be null")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MAP_EMPLOYEE_DEPARTMENT",
            joinColumns = @JoinColumn(name = "ID_EMPLOYEE"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPARTMENT")
    )
    private Set<Department> departmentSet;
}
