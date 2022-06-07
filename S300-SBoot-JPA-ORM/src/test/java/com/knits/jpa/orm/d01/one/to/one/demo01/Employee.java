package com.knits.jpa.orm.d01.one.to.one.demo01;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;

}