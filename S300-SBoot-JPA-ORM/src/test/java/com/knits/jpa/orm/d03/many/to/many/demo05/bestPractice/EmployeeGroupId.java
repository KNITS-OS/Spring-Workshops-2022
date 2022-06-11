package com.knits.jpa.orm.d03.many.to.many.demo05.bestPractice;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
/*You need the @Embeddable type to be Serializable
The @Embeddable type must override the default equals and
hashCode methods based on the two Primary Key identifier values.*/
public class EmployeeGroupId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "group_id")
    private Long groupId;

    private EmployeeGroupId() {}

    public EmployeeGroupId(Long employeeId, Long groupId) {
        this.employeeId = employeeId;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EmployeeGroupId that = (EmployeeGroupId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, groupId);
    }
}
