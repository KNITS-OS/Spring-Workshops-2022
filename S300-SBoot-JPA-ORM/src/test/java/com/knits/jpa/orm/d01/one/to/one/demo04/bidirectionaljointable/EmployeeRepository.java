package com.knits.jpa.orm.d01.one.to.one.demo04.bidirectionaljointable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
