package com.knits.product.repository;


import com.knits.product.model.Group;
import com.knits.product.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

}
