package com.knits.jpa.orm.d02.one.to.many.demo01;

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

    @OneToMany(mappedBy = "project")
    private List<Employee> employees= new ArrayList<>();
}
