package com.knits.product.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"team\"") // "team" is used as the table name
@NamedEntityGraph(name = "Team.users",
        attributeNodes = @NamedAttributeNode("users"))
public class Team {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    private List<User> users;

    @Column(nullable = false)
    private Boolean active = false;
}
