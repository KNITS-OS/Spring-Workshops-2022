package com.knits.jpa.orm.d02.one.to.many.demo05.Sets;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;


    /*It's important to @Override the equals() and hashCode() method since the Author entity relies on
     checking equality for it's helper methods...*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee )) return false;
        return id != null && id.equals(((Employee) o).getId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
