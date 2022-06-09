package com.knits.jpa.orm.d03.many.to.many.demo03;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "EmployeeGroup")
@Table(name = "employee_group")
@Data
public class EmployeeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_fk")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "employee_fk")
    private Employee employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EmployeeGroup)) return false;

        EmployeeGroup other = (EmployeeGroup) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // additional properties if necessary
}
