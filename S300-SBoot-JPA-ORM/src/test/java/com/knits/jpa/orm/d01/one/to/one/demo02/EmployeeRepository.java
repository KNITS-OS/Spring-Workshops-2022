package com.knits.jpa.orm.d01.one.to.one.demo02;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public Employee getEmployeeByOffice(Office office);
}
