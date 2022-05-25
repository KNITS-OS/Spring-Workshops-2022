package com.knits.jpa.orm.d02.one.to.many.demo01;

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
@EntityScan("com.knits.jpa.orm.d02.one.to.many.demo01")
@EnableJpaRepositories("com.knits.jpa.orm.d02.one.to.many.demo01")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-02"
})
@Slf4j
public class TestJpaOneToManyDemo01 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @Rollback(value = false)
    public void initDatabase(){



        Project project = new Project();
        project.setName("A Mock Project Name");

        Employee employee = new Employee();
        employee.setFirstName("Stefano");
        employee.setLastName("Fiorenza");


        Employee employee2 = new Employee();
        employee2.setFirstName("Luna");
        employee2.setLastName("Doria");

        //connect both sides
        employee.setProject(project);
        employee2.setProject(project);
        project.getEmployees().add(employee);
        project.getEmployees().add(employee2);

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        Project savedProject =projectRepository.save(project);

        Project foundProject =projectRepository.findById(savedProject.getId()).get();

        log.info("Employees for Project: {} ",foundProject.getName());
        foundProject.getEmployees().forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));

    }
}
