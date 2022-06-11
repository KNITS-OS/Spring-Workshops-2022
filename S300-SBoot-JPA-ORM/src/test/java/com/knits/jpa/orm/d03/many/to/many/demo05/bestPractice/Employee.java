package com.knits.jpa.orm.d03.many.to.many.demo05.bestPractice;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EmployeeGroup> groups = new ArrayList<>();

    public Employee() {
    }

    public Employee(String lastName) {
        this.lastName = lastName;
    }

    public void addGroup(Group group) {
        EmployeeGroup employeeGroup = new EmployeeGroup(this, group);
        groups.add(employeeGroup);
        group.getEmployees().add(employeeGroup);
    }

    public void removeGroup(Group group) {
        for (Iterator<EmployeeGroup> iterator = groups.iterator();
             iterator.hasNext(); ) {
            EmployeeGroup employeeGroup = iterator.next();

            if (employeeGroup.getEmployee().equals(this) &&
                    employeeGroup.getGroup().equals(group)) {
                iterator.remove();
                employeeGroup.getGroup().getEmployees().remove(employeeGroup);
                employeeGroup.setEmployee(null);
                employeeGroup.setGroup(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Employee employee = (Employee) o;
        return Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName);
    }
}
