package com.knits.jpa.queries.model;

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

    @ManyToOne()
    @JoinColumn(name = "country_id")
    private Country country;

}
