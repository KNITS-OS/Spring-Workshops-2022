package com.knits.product.repository;


import com.knits.product.model.Team;
import com.knits.product.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

    //@EntityGraph(type= EntityGraph.EntityGraphType.FETCH, value="Team.users")
    @Query("select t from Team t")
    List<Team> findWithUsers();
}
