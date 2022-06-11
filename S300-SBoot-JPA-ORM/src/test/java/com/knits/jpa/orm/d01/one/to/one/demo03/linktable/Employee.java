package com.knits.jpa.orm.d01.one.to.one.demo03.linktable;

import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    @JoinTable(name = "EMPLOYEE_OFFICE_JOIN_TABLE",
            joinColumns = {
                    @JoinColumn(name = "EMPLOYEE_FK", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "OFFICE_FK", referencedColumnName = "id", unique = true)
            }
    )
    private Office office;

}
