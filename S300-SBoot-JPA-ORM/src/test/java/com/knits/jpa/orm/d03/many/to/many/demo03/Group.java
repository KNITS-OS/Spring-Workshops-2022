package com.knits.jpa.orm.d03.many.to.many.demo03;

import lombok.Data;

import javax.persistence.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="\"group\"") //group is a reserved word in sql
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<EmployeeGroup> employees = new LinkedHashSet<>();

    public EmployeeGroup addEmployee(Employee employee) {
        EmployeeGroup employeeGroup = new EmployeeGroup();
        employeeGroup.setGroup(this);
        employeeGroup.setEmployee(employee);
        employees.add(employeeGroup);
        employee.getGroups().add(employeeGroup);

        return employeeGroup;
    }

    public void removeEmployee(Employee employee) {
        for (Iterator<EmployeeGroup> iterator = employees.iterator(); iterator.hasNext();) {
            EmployeeGroup employeeGroup = iterator.next();

            if (employeeGroup.getGroup().equals(this) &&
                employeeGroup.getEmployee().equals(employee)) {
                iterator.remove();
                employeeGroup.getEmployee().getGroups().remove(employeeGroup);
                employeeGroup.setGroup(null);
                employeeGroup.setEmployee(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Group)) return false;

        Group other = (Group) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
