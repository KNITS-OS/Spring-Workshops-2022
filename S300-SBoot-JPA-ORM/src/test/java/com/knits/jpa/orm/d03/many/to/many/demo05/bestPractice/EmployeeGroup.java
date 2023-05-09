package com.knits.jpa.orm.d03.many.to.many.demo05.bestPractice;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "EmployeeGroup")
@Table(name = "employee_group")
@Data
public class EmployeeGroup {

    @EmbeddedId
    private EmployeeGroupId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    private Group group;

    @Column(name = "created_on")
    private Date createdOn = new Date();

    private EmployeeGroup() {

    }

    public EmployeeGroup(Employee employee, Group group) {
        this.employee = employee;
        this.group = group;
        this.id = new EmployeeGroupId(employee.getId(), group.getId());
    }

    public EmployeeGroupId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EmployeeGroup that = (EmployeeGroup) o;
        return Objects.equals(employee, that.employee) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, group);
    }
}
