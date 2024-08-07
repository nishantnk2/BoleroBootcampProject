package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "NAME_FIRST", nullable = false)
    private String firstName;

    @Column(name = "NAME_LAST", nullable = false)
    private String lastName;
}
