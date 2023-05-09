package com.knits.jpa.orm.d02.one.to.many.demo02;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// one to many with link table

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d02.one.to.many.demo02")
@EnableJpaRepositories("com.knits.jpa.orm.d02.one.to.many.demo02")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_orm_2"
})
@Slf4j
public class TestJpaOneToManyDemo02 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // added for simplicity
    private Map<String, Object> createFakeEmployeeAndOffice() {
        Faker faker = new Faker();

        Project project = new Project();
        project.setName(faker.app().name());

        Set<Employee> employees = new HashSet<>();

        for (int i = 0; i < 2; i++) {
            // create employee
            Employee employee = new Employee();
            employee.setFirstName(faker.name().firstName());
            employee.setLastName(faker.name().lastName());

            // connect
            project.getEmployees().add(employee);

            // save and add to the set
            employees.add(employeeRepository.save(employee));
        }

        Project savedProject = projectRepository.save(project);

        log.info("Employees for Project: {} ",savedProject.getName());
        savedProject.getEmployees()
                .forEach(emp -> log.info("Employee Found: {} ", emp.getFirstName()));

        return new HashMap<>() {{
            put("project", savedProject);
            put("employees", employees);
        }};
    }

    @Test
    @Rollback(value = false)
    public void initDatabase() {
        Project project = new Project();
        project.setName("A Mock Project Name");

        Employee employee = new Employee();
        employee.setFirstName("Stefano");
        employee.setLastName("Fiorenza");

        Employee employee2 = new Employee();
        employee2.setFirstName("Luna");
        employee2.setLastName("Doria");

        project.getEmployees().add(employee);
        project.getEmployees().add(employee2);

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        Project savedProject = projectRepository.save(project);

        log.info("Employees for Project: {} ",savedProject.getName());
        savedProject.getEmployees()
                .forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void updateEmployeeByProject() {
        Faker faker = new Faker();
        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved project
        Project project = (Project) created.get("project");

        // update
        if (project.getEmployees().isEmpty())
            return;

        Employee employee = project.getEmployees().iterator().next();
        log.info("Employee name: {}", employee.getFirstName());

        employee.setFirstName(faker.name().firstName());
        log.info("Updated employee name: {}", employee.getFirstName());

        employeeRepository.save(employee);
    }

    @Test
    @Rollback(value = false)
    public void deleteEmployeeByProject() {
        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved project
        Project project = (Project) created.get("project");

        // delete
        if (project.getEmployees().isEmpty())
            return;

        Employee employee = project.getEmployees().iterator().next();
        log.info("Employee name to be removed: {}, id: {}",
                employee.getFirstName(), employee.getId());

        project.getEmployees().remove(employee);
        employeeRepository.delete(employee);
        if (project.getEmployees().size() == 0) {
            projectRepository.delete(project);
            return;
        }
        projectRepository.save(project);
    }

    @Test
    @Rollback(value = false)
    public void findEmployeeByProject() {
        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved project
        Project project = (Project) created.get("project");

        // find
        Employee employee = project.getEmployees().iterator().next();
        log.info("Employee name: {}", employee.getFirstName());
    }
}
