package com.knits.jpa.orm.d02.one.to.many.demo05.Sets;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setProject(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setProject(null);

    }

}
