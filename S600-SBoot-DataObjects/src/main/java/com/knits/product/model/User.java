package com.knits.product.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A user.
 */
@Entity
@Table(name = "\"user\"") //user is a reserved word in postgres sql
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(length = 50, unique = true)
    private String login;

    @Column(name = "password_hash", length = 60)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 254, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean active = false;
    @ManyToOne
    private Team team;

//    public void setTeam(Team team) {
//        this.team=team;
//        this.team.getUsers().add(this);
//        // this.team.addUser(this); //this will cause cycle
//    }

}