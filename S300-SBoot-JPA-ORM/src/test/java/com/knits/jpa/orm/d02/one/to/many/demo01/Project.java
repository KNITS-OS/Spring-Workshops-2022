package com.knits.jpa.orm.d02.one.to.many.demo01;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;
}
