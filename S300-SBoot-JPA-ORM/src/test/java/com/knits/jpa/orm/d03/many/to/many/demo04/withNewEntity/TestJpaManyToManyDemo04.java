package com.knits.jpa.orm.d03.many.to.many.demo04.withNewEntity;


import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo04.*")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo04.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-03-03"
})
@Slf4j
//help to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaManyToManyDemo04 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupRegistrationRepository groupRegistrationRepository;

    @BeforeEach
    @Rollback(value = false)
    public void initDatabase(){

        Employee employee= new Employee();
        employee.setFirstName("Stefano");
        employee.setLastName("Fiorenza");

        Employee employee2 = new Employee();
        employee2.setFirstName("Luna");
        employee2.setLastName("Doria");

        Employee employee3 = new Employee();
        employee3.setFirstName("Maria");
        employee3.setLastName("Bertolucci");

        Group group = new Group();
        group.setName("A Mock Group Name");

        Group group2 = new Group();
        group2.setName("Another Mock Group Name");

        //create registration object
        GroupRegistration groupRegistration = new GroupRegistration();
        groupRegistration.setRegisteredAt(LocalDateTime.now());

        GroupRegistration groupRegistration2 = new GroupRegistration();
        groupRegistration2.setRegisteredAt(LocalDateTime.now());

        GroupRegistration groupRegistration3 = new GroupRegistration();
        groupRegistration3.setRegisteredAt(LocalDateTime.now());

        GroupRegistration groupRegistration4 = new GroupRegistration();
        groupRegistration4.setRegisteredAt(LocalDateTime.now());

        //Use helper methods (add and set)
        employee.addRegistration(groupRegistration);
        employee.addRegistration(groupRegistration2);
        employee2.addRegistration(groupRegistration3);
        employee3.addRegistration(groupRegistration4);

        group.addRegistration(groupRegistration);
        group2.addRegistration(groupRegistration2);
        group.addRegistration(groupRegistration3);
        group2.addRegistration(groupRegistration4);

        //save two groups
        groupRepository.save(group);
        groupRepository.save(group2);

        //save three employees
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //save 4 registrations
        groupRegistrationRepository.save(groupRegistration);
        groupRegistrationRepository.save(groupRegistration2);
        groupRegistrationRepository.save(groupRegistration3);
        groupRegistrationRepository.save(groupRegistration4);


        log.info("Employee: {} ",savedEmployee.getFirstName());
        savedEmployee.getRegistrations().forEach(reg -> log.info("Registered in group: {} ",reg.getGroup().getName()));

    }

    @Test
    @Rollback(value = false)
    public void getGroupRegistration() {

        GroupRegistration groupRegistration = groupRegistrationRepository.findById(102L).get();

        Assertions.assertThat(groupRegistration.getId()).isEqualTo(102L);
    }

    @Test
    @Rollback(value = false)
    public void getGroup() {

        Group group = groupRepository.findById(1L).get();

        Assertions.assertThat(group.getId()).isEqualTo(1L);
    }

    @Test
    @Rollback(value = false)
    public void getEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getId()).isEqualTo(52L);
    }


    @Test
    @Rollback(value = false)
    public void getEmployeeWithRegistrations() {

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getRegistrations().size()).isGreaterThan(0);
    }

    @Test
    @Rollback(value = false)
    public void getListOfGroups() {

        List<Group> groups = groupRepository.findAll();

        Assertions.assertThat(groups.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    public void updateEmployeeAndGroup() {

        Employee employee = employeeRepository.findById(52L).get();

        employee.setFirstName("Anton");
        employee.getRegistrations().forEach(groupRegistration -> groupRegistration.getGroup().setName("SuperGroup"));

        Employee employeeUpdated = employeeRepository.save(employee);
        Group groupUpdated = groupRepository.findById(1L).get();

        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");
        Assertions.assertThat(groupUpdated.getName()).isEqualTo("SuperGroup");

    }

    @Test
    @Rollback(value = false)
    //can not delete an employee who in the reg_group
    //should to delete employee registrations then delete employee
    public void deleteEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

        GroupRegistration groupRegistration = groupRegistrationRepository.findById(102L).get();
        GroupRegistration groupRegistration2 = groupRegistrationRepository.findById(103L).get();

        groupRegistrationRepository.delete(groupRegistration);
        groupRegistrationRepository.delete(groupRegistration2);

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = employeeRepository.findById(52L);

        if (optionalEmployee.isPresent()) {
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

    @Test
    @Rollback(value = false)
    //can not delete a group if its in the reg_group
    //should to delete group registrations then delete group
    public void deleteGroup() {

        Group group = groupRepository.findById(1L).get();

        GroupRegistration groupRegistration = groupRegistrationRepository.findById(102L).get();
        GroupRegistration groupRegistration2 = groupRegistrationRepository.findById(104L).get();

        groupRegistrationRepository.delete(groupRegistration);
        groupRegistrationRepository.delete(groupRegistration2);

        groupRepository.delete(group);

        Group group1 = null;

        Optional<Group> optionalGroup = groupRepository.findById(1L);

        if (optionalGroup.isPresent()) {
            group1 = optionalGroup.get();
        }

        Assertions.assertThat(group1).isNull();
    }

}
