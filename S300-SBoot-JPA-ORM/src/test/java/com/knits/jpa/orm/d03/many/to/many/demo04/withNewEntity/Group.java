package com.knits.jpa.orm.d03.many.to.many.demo04.withNewEntity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="\"group\"") //group is a reserved word in sql
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<GroupRegistration> registrations = new HashSet<>();

    public void addRegistration(GroupRegistration groupRegistration) {
        registrations.add(groupRegistration);
        groupRegistration.setGroup(this);
    }

    public void removeRegistration(GroupRegistration groupRegistration) {
        registrations.remove(groupRegistration);
        groupRegistration.setGroup(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
