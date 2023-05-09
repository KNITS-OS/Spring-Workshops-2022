package com.knits.jpa.orm.d01.one.to.one.demo02;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Office office;

}
