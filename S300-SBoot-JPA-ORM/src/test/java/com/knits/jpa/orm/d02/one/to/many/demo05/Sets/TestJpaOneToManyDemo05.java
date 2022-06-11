package com.knits.jpa.orm.d02.one.to.many.demo05.Sets;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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
@EntityScan("com.knits.jpa.orm.d02.one.to.many.demo05.*")
@EnableJpaRepositories("com.knits.jpa.orm.d02.one.to.many.demo05.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-02-05"
})
@Slf4j
//to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaOneToManyDemo05 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @BeforeEach
    @Rollback(value = false)
    public void initDatabase() {

        Project project = new Project();
        project.setName("A Mock Project Name");

        Employee employee1 = new Employee();
        employee1.setFirstName("Stefano");
        employee1.setLastName("Fiorenza");

        Employee employee2 = new Employee();
        employee2.setFirstName("Luna");
        employee2.setLastName("Doria");

        //it works, but project_id in Employee table is null
        /*project.getEmployees().add(employee);
        project.getEmployees().add(employee2);*/

        /*The helper methods addBook() and removeBook() are useful in keeping the persisted context in sync.
         Strange things can happen when both sides of the bidirectional relationship aren't updated.
         The database can get out of sync with the context and unexpected things can happen.
         These methods make it easy to update both sides of the relationship...*/
        //via helper methods in model project_id is not null
        project.addEmployee(employee1);
        project.addEmployee(employee2);

        Project savedProject = projectRepository.save(project);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        log.info("Employees for Project: {} ", savedProject.getName());
        savedProject.getEmployees().forEach(emp -> log.info("Employee Found: {} ", emp.getFirstName()));

    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void getProjectWithEmployees() {

        Project project = projectRepository.findById(1L).get();

        Assertions.assertThat(project.getEmployees().size()).isGreaterThan(0);

    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getListOfProjects() {

        List<Project> projects = projectRepository.findAll();

        Assertions.assertThat(projects.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    @Order(3)
    public void updateEmployeeAndProject() {

        Project project = projectRepository.findById(1L).get();

        project.setName("Mock 2");
        project.getEmployees().forEach(emp -> emp.setFirstName("Anton"));

        Project projectUpdated = projectRepository.save(project);
        Employee employeeUpdated = employeeRepository.findById(52L).get();

        Assertions.assertThat(projectUpdated.getName()).isEqualTo("Mock 2");
        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");

    }

    @Test
    @Rollback(value = false)
    @Order(4)
    public void deleteEmployee() {

        Employee employee = employeeRepository.findById(53L).get();

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = employeeRepository.findById(53L);

        if (optionalEmployee.isPresent()) {
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();

    }


    @Test
    @Rollback(value = false)
    @Order(5)
    public void deleteProjectShouldNotDeleteEmployees() {

        Project project = projectRepository.findById(1L).get();

        Employee employee = employeeRepository.findById(52L).get();
        Employee employee1 = employeeRepository.findById(53L).get();

        project.removeEmployee(employee);
        project.removeEmployee(employee1);

        projectRepository.delete(project);

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    @Order(6)
    //since I have a  project like parent, all children (employees) deletes with project
    public void deleteProject() {

        Project project = projectRepository.findById(1L).get();

        projectRepository.delete(project);

        Project project1 = null;

        Optional<Project> optionalProject = projectRepository.findById(1L);

        if (optionalProject.isPresent()) {
            project1 = optionalProject.get();
        }

        Assertions.assertThat(project1).isNull();

    }
}
