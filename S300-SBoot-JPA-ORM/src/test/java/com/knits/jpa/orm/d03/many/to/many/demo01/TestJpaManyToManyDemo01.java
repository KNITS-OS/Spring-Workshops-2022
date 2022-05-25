package com.knits.jpa.orm.d03.many.to.many.demo01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;


@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo01")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo01")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-03"
})
@Slf4j
public class TestJpaManyToManyDemo01 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
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
        group.getEmployees().add(employee);
        group.getEmployees().add(employee2);

        //emp 3 is in group2
        employee3.getGroups().add(group2);
        group2.getEmployees().add(employee3);

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Group savedGroup =groupRepository.save(group);
        groupRepository.save(group2);

        log.info("Employees for Group: {} ",savedGroup.getName());
        savedGroup.getEmployees().forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));

    }
}
