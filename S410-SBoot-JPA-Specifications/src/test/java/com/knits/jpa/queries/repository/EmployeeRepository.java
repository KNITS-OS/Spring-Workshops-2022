package com.knits.jpa.queries.repository;

import com.knits.jpa.queries.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    public List<Employee> findByFirstName(String firstName);

    public List<Employee> findByEmail(String email);

    public List<Employee> readByFirstName(String firstName);

    public List<Employee> queryByFirstName(String firstName);

    public List<Employee> getByFirstName(String firstName);

    public List<Employee> findByOffice_Country_Iso2(String iso2);


    public int countByFirstName(String firstName);




    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1")
    public List<Employee> findEmployeesByFirstName(String fname);


    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName")
    public List<Employee> findEmployeesByFirstNameWithParameter(@Param("firstName") String firstName);

    @Query("SELECT e FROM Employee e WHERE e.office.country.iso2 = ?1")
    public List<Employee> findByIso2(String iso2);


}
