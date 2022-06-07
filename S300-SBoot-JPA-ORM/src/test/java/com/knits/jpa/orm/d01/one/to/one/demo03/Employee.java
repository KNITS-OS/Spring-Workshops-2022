package com.knits.jpa.orm.d01.one.to.one.demo03;

import lombok.Data;

import javax.persistence.*;

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
    @JoinTable(name = "employee_office_join_table",
            joinColumns = {
                @JoinColumn(name = "employee_fk", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "office_fk", referencedColumnName = "id", unique = true)
            }
    )
    private Office office;

}
