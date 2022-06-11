package com.knits.jpa.orm.d02.one.to.many.demo04.bidirectionalJoinTable;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    /*
       By default, a bidirectional OneToMany/ManyToOne association uses a foreign
       key column on the side which has the single-valued reference with @ManyToOne annotation.
       We can override this default by using an intermediate join table to persist the association.
       To achieve that we have to use @JoinTable annotation on the both sides.
       In this case, we don't have to use 'mappedBy' element of the @OneToMany annotation.

       Note that we have to use insertable/updatable=false on @ManyToOne side,
       to avoid persisting same relationship twice from both sides. When insertable/updatable=false,
       the column is not included in the generated SQL INSERT/UPDATE statements.
    */
    @ManyToOne
    @JoinTable(name = "EMPLOYEE_PROJECT_TABLE",
            joinColumns = {@JoinColumn(name = "EMPLOYEE_FK", insertable = false,
                    updatable = false, referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "PROJECT_FK", insertable = false,
                    updatable = false, referencedColumnName = "id")}
    )
    private Project project;
}
