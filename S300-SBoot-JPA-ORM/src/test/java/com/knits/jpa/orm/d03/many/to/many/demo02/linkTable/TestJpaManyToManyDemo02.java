package com.knits.jpa.orm.d03.many.to.many.demo02.linkTable;

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

import java.util.List;
import java.util.Optional;

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo02.*")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo02.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-03-01"
})
@Slf4j
//help to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaManyToManyDemo02 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

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

        //emp 1 and 2 are in group1
        employee.getGroups().add(group);
        employee2.getGroups().add(group);

        //emp 3 and 1 are in group2
        employee3.getGroups().add(group2);
        employee.getGroups().add(group2);

        groupRepository.save(group);
        groupRepository.save(group2);

        Employee savedEmployee = employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        log.info("Employee: {} ",savedEmployee.getFirstName());
        savedEmployee.getGroups().forEach(gr -> log.info("Groups Found: {} ",gr.getName()));

    }

    @Test
    @Rollback(value = false)
    public void getEmployeeWithGroups() {

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
    public void updateEmployeeAndGroup() {

        Employee employee = employeeRepository.findById(52L).get();

        employee.setFirstName("Anton");
        employee.getGroups().forEach(emp -> emp.setName("Group updated"));

        Employee employeeUpdated = employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");
        Assertions.assertThat(employeeUpdated.getGroups().get(1).getName()).isEqualTo("Group updated");

    }

    @Test
    @Rollback(value = false)
    //can delete an employee who in the group
    public void deleteEmployee() {

        Employee employee = employeeRepository.findById(52L).get();

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
    //can not delete the group/target with employees/source
    //should remove employees from group then can delete group
    public void removeEmployeesFromGroupThenDeleteGroup() {

        Employee employee = employeeRepository.findById(52L).get();
        Employee employee1 = employeeRepository.findById(53L).get();

        Group group = groupRepository.findById(1L).get();

        employee.getGroups().remove(group);
        employee1.getGroups().remove(group);

        groupRepository.delete(group);

        Group group1 = null;

        Optional<Group> optionalGroup = groupRepository.findById(1L);

        if (optionalGroup.isPresent()) {
            group1 = optionalGroup.get();
        }

        Assertions.assertThat(group1).isNull();

    }

}
