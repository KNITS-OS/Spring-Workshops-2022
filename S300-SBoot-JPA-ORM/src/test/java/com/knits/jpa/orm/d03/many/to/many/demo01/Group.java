package com.knits.jpa.orm.d03.many.to.many.demo01;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="\"group\"") //group is a reserved word in sql
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;
}
