package com.knits.jpa.orm.d02.one.to.many.demo01;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Set<Employee> findAllByProject(Project project);
}
