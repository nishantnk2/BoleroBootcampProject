package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE_ID")
    @SequenceGenerator(name = "SEQ_EMPLOYEE_ID", sequenceName = "SEQ_EMPLOYEE_ID", allocationSize = 1)
    private Integer id;

    @NotNull(message = "FIRSTNAME cannot be null")
    @Column(name = "NAME_FIRST", nullable = false)
    private String nameFirst;

    @NotNull(message = "LASTNAME cannot be null")
    @Column(name = "NAME_LAST", nullable = false)
    private String nameLast;

    @NotNull(message = "DEPARTMENT cannot be null")
    @ManyToMany
    @JoinTable(
            name = "MAP_EMPLOYEE_DEPARTMENT",
            joinColumns = @JoinColumn(name = "ID_EMPLOYEE"),
            inverseJoinColumns = @JoinColumn(name = "ID_DEPARTMENT")
    )
    private Set<Department> departments;
}
