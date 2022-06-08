package com.knits.jpa.orm.d02.one.to.many.demo02;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "project_employees_link_table",
            joinColumns = {
                @JoinColumn(name = "project_fk")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "employee_fk")
            }
    )
    private Set<Employee> employees = new LinkedHashSet<>();
}
