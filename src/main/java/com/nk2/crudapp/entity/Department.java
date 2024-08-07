package com.nk2.crudapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MANDATORY", nullable = false)
    private boolean mandatory;

    @Column(name = "READ_ONLY", nullable = false)
    private boolean readOnly;
}
