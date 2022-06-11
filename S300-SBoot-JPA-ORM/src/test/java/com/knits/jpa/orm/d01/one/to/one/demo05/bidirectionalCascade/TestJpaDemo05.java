package com.knits.jpa.orm.d01.one.to.one.demo05.bidirectionalCascade;


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

import java.util.Optional;

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d01.one.to.one.demo05.*") //otherwise finds all other entities in subpackages
@EnableJpaRepositories("com.knits.jpa.orm.d01.one.to.one.demo05.*") //otherwise doesnt create jpa repositories instances
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-05"
})
//to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaDemo05 {

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

        office.setEmployee(employee);
        employee.setOffice(office);

        officeRepository.save(office);
        employeeRepository.save(employee);

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
    //Can delete office and employee by cascade
    public void deleteOffice(){

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
