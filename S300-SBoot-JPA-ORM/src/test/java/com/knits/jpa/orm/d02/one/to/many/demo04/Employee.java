package com.knits.jpa.orm.d02.one.to.many.demo04;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinTable(name = "project_employees_link_table",
            joinColumns = {
                @JoinColumn(name = "employee_fk", insertable = false, updatable = false, referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "project_fk", insertable = false, updatable = false, referencedColumnName = "id")
            }
    )
    private Project project;
}
