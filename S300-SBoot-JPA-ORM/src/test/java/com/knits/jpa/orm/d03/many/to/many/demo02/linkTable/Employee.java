package com.knits.jpa.orm.d03.many.to.many.demo02.linkTable;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
/*The entity having the collection reference is called 'source' entity',
whereas the entity which is being referenced is called 'target entity'.
To map many-to-many association to database tables,
@ManyToMany annotation is used on the collection.*/
//Source entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany
    private List<Group> groups = new ArrayList<>(); //Group is target entity
}
