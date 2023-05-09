package com.knits.jpa.orm.d02.one.to.many.demo04.bidirectionalJoinTable;

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


    /*
       By default, a bidirectional OneToMany/ManyToOne association uses a foreign
       key column on the side which has the single-valued reference with @ManyToOne annotation.
       We can override this default by using an intermediate join table to persist the association.
       To achieve that we have to use @JoinTable annotation on the both sides.
       In this case, we don't have to use 'mappedBy' element of the @OneToMany annotation.
    */
    @OneToMany
    @JoinTable(name = "EMPLOYEE_PROJECT_TABLE",
            joinColumns = {@JoinColumn(name = "PROJECT_FK", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_FK", referencedColumnName = "id")}
    )
    private List<Employee> employeeList = new ArrayList<>();
}
