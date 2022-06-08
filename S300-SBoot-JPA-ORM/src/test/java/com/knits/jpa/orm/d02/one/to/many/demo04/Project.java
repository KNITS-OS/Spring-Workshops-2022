package com.knits.jpa.orm.d02.one.to.many.demo04;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "project_employees_link_table",
            joinColumns = {
                @JoinColumn(name = "project_fk", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "employee_fk", referencedColumnName = "id")
            }
    )
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Project))
            return false;

        Project other = (Project) o;

        return id != null && id.equals(other.getId());
    }
}
