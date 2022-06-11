package com.knits.jpa.orm.d03.many.to.many.demo05.bestPractice;

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
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo05.*")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo05.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-03-04"
})
@Slf4j
//help to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaManyToManyDemo05 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EmployeeGroupRepository employeeGroupRepository;

    @BeforeEach
    @Rollback(value = false)
    public void initDatabase() {

        Employee employee = new Employee();
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

        //save two groups
        groupRepository.save(group);
        groupRepository.save(group2);

        //save employee
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        employee.addGroup(group);
        employee.addGroup(group2);
        employee2.addGroup(group);
        employee3.addGroup(group2);

        log.info("Employee: {} ", savedEmployee.getFirstName());
        savedEmployee.getGroups().forEach(reg -> log.info("Registered in group: {} ", reg.getGroup().getName()));

    }

    @Test
    @Rollback(value = false)
    public void getEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getId()).isEqualTo(52L);
    }

    @Test
    @Rollback(value = false)
    public void getGroup() {

        Group group = groupRepository.findById(1L).get();

        Assertions.assertThat(group.getId()).isEqualTo(1L);
    }

    @Test
    @Rollback(value = false)
    public void getEmployeeWithGroup() {

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getGroups().size()).isGreaterThan(0);
    }

    @Test
    @Rollback(value = false)
    public void getListOfGroups() {

        List<Group> groups = groupRepository.findAll();

        Assertions.assertThat(groups.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    public void addEmployeeIntoGroup() {

        Employee employee = employeeRepository.findById(53L).get();
        Group group = groupRepository.findById(2L).get();
        employee.addGroup(group);

        Assertions.assertThat(employee.getGroups().get(1).getGroup().getId()).isEqualTo(2L);

    }

    @Test
    @Rollback(value = false)
    public void updateEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

        employee.setFirstName("Anton");
        employee.setLastName("Mezhenin");

        Employee employeeUpdated = employeeRepository.findById(52L).get();

        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");
        Assertions.assertThat(employeeUpdated.getLastName()).isEqualTo("Mezhenin");

    }

    @Test
    @Rollback(value = false)
    public void removeEmployeeFromGroup() {

        Employee employee = employeeRepository.findById(52L).get();

        Group group = groupRepository.findById(1L).get();
        Group group1 = groupRepository.findById(2L).get();

        employee.removeGroup(group);
        employee.removeGroup(group1);

        Employee employee1 = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getGroups().size()).isEqualTo(0);
    }

    @Test
    @Rollback(value = false)
    //can delete employee if employee without groups
    public void deleteEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

        Group group = groupRepository.findById(1L).get();
        Group group1 = groupRepository.findById(2L).get();

        employee.removeGroup(group);
        employee.removeGroup(group1);

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
    //can delete group if it is empty
    public void deleteGroup() {

        Group group = groupRepository.findById(1L).get();

        Employee employee = employeeRepository.findById(53L).get();
        Employee employee2 = employeeRepository.findById(52L).get();

        employee.removeGroup(group);
        employee2.removeGroup(group);

        groupRepository.delete(group);

        Group group1 = null;

        Optional<Group> optionalGroup = groupRepository.findById(1L);

        if (optionalGroup.isPresent()) {
            group1 = optionalGroup.get();
        }

        Assertions.assertThat(group1).isNull();

    }


}
