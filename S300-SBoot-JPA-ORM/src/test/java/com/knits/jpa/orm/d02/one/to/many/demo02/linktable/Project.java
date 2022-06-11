package com.knits.jpa.orm.d02.one.to.many.demo02.linktable;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "EMPLOYEE_PROJECT_TABLE",
            joinColumns = {@JoinColumn(name = "PROJECT_FK")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_FK")}
    )
    private List<Employee> employeeList = new ArrayList<>();
}
