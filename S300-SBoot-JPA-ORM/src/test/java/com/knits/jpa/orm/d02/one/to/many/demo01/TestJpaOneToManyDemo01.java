package com.knits.jpa.orm.d02.one.to.many.demo01;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@EntityScan("com.knits.jpa.orm.d02.one.to.many.demo01")
@EnableJpaRepositories("com.knits.jpa.orm.d02.one.to.many.demo01")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-02-01"
})
@Slf4j
//to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaOneToManyDemo01 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    @Rollback(value = false)
    //In Db Employee table we can see project_id
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

        log.info("Employees for Project: {} ",savedProject.getName());
        savedProject.getEmployees().forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));

    }

    @Test
    public void getProjectEmployeeList(){

        Project project = projectRepository.findById(52L).get();

        Assertions.assertThat(project.getEmployees().size()).isGreaterThan(0);
    }

    @Test
    public void getListOfProjectsTest(){

        List<Project> projects = projectRepository.findAll();

        Assertions.assertThat(projects.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    public void updateEmployeeAndProjectTest(){

        Project project = projectRepository.findById(52L).get();

        project.setName("Mock 2");
        project.getEmployees().forEach(emp -> emp.setFirstName("Anton"));

        Project projectUpdated = projectRepository.save(project);
        Employee employeeUpdated = projectUpdated.getEmployees().get(1);

        Assertions.assertThat(projectUpdated.getName()).isEqualTo("Mock 2");
        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");

    }

    @Test
    @Rollback(value = false)
    //can delete employee if he in relationships with project
    public void deleteEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();

        employeeRepository.delete(employee);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = employeeRepository.findById(1L);

        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

    @Test
    @Rollback(value = false)
    //Can't delete a project if it has employees
    //must first delete the employees and then the project
    public void deleteProjectTest(){

        Employee employee = employeeRepository.findById(1L).get();
        Employee employee1 = employeeRepository.findById(2L).get();

        employeeRepository.delete(employee);
        employeeRepository.delete(employee1);

        Project project = projectRepository.findById(52L).get();
        projectRepository.delete(project);

        Project project1 = null;

        Optional<Project> optionalProject = projectRepository.findById(52L);

        if(optionalProject.isPresent()){
            project1 = optionalProject.get();
        }

        Assertions.assertThat(project1).isNull();

    }
}
