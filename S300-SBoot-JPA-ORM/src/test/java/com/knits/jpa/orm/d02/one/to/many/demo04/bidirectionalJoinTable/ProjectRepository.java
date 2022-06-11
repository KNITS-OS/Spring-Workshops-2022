package com.knits.jpa.orm.d02.one.to.many.demo04.bidirectionalJoinTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

}
