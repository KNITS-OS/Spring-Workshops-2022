package com.knits.jpa.orm.d01.one.to.one.demo05.bidirectionalCascade;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private String city;

    private String street;

    @OneToOne(
            mappedBy = "office",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Employee employee;
}
