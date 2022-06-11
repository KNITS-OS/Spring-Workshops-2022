package com.knits.jpa.orm.d01.one.to.one.demo03.linktable;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d01.one.to.one.demo03.*") //otherwise finds all other entities in subpackages
@EnableJpaRepositories("com.knits.jpa.orm.d01.one.to.one.demo03.*") //otherwise doesnt create jpa repositories instances
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-03"
})
//to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaDemo03 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @BeforeEach
    @Rollback(value = false)
    public void initDatabase(){

        Office office = new Office();
        office.setCity("A Mock City");
        office.setStreet("A Mock Street");

        Employee employee = new Employee();
        employee.setFirstName("Anton");
        employee.setLastName("Mezhenin");
        employee.setOffice(office);

        officeRepository.save(office);
        employeeRepository.save(employee);

    }

    @Test
    @Rollback(value = false)
    public void getEmployee(){

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getId()).isEqualTo(52L);
    }

    @Test
    @Rollback(value = false)
    public void getEmployeeWithOfficeId(){

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getOffice().getId()).isEqualTo(1L);
    }

    @Test
    public void getListOfEmployees(){

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    public void updateEmployee(){

        Employee employee = employeeRepository.findById(52L).get();

        employee.setLastName("Great");

        Employee employeeUpdated =  employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdated.getLastName()).isEqualTo("Great");

    }

    @Test
    @Rollback(value = false)
    //can delete data from Employee and join table without conflicts. Office exists
    public void deleteEmployee(){

        Employee employee = employeeRepository.findById(52L).get();

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = employeeRepository.findById(52L);

        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

    @Test
    @Rollback(value = false)
    //Firstly have to delete employee and after delete office
    public void deleteOffice(){

        Employee employee = employeeRepository.findById(52L).get();

        employeeRepository.delete(employee);

        Office office = officeRepository.findById(1L).get();

        officeRepository.delete(office);

        Office office1 = null;

        Optional<Office> optionalOffice = officeRepository.findById(1L);

        if(optionalOffice.isPresent()){
            office1 = optionalOffice.get();
        }

        Assertions.assertThat(office1).isNull();
    }
}
