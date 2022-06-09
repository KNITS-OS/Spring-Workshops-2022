package com.knits.jpa.orm.d03.many.to.many.demo02;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="\"group\"") //group is a reserved word in sql
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "employees_groups_join_table",
            joinColumns = @JoinColumn(name = "group_fk", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_fk", referencedColumnName = "id")
    )
    private Set<Employee> employees = new LinkedHashSet<>();
}
