package com.knits.jpa.orm.d02.one.to.many.demo03.FKey;


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
@EntityScan("com.knits.jpa.orm.d02.one.to.many.demo03.*")
@EnableJpaRepositories("com.knits.jpa.orm.d02.one.to.many.demo03.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-02-03"
})
@Slf4j
//to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestJpaOneToManyDemo03 {

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

        employee.setProject(project);
        employee2.setProject(project);

        projectRepository.save(project);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

    }

    @Test
    @Rollback(value = false)
    public void getEmployeeWithProject(){

        Employee employee = employeeRepository.findById(52L).get();

        Assertions.assertThat(employee.getProject().getName()).isEqualTo("A Mock Project Name");
    }

    @Test
    @Rollback(value = false)
    public void getListOfEmployees(){

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);

    }

    @Test
    @Rollback(value = false)
    public void updateEmployeeAndProject(){

        Employee employee = employeeRepository.findById(52L).get();

        employee.getProject().setName("Mock 2");
        employee.setFirstName("Anton");

        Employee employeeUpdated = employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdated.getFirstName()).isEqualTo("Anton");
        Assertions.assertThat(employeeUpdated.getProject().getName()).isEqualTo("Mock 2");

    }

    @Test
    @Rollback(value = false)
    //can delete employee with project reference
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
    //I can't delete a project if it has employees
    public void deleteProject(){

        Employee employee = employeeRepository.findById(53L).get();
        employee.setProject(null);

        Employee employee2 = employeeRepository.findById(52L).get();
        employee2.setProject(null);

        Project project = projectRepository.findById(1L).get();
        projectRepository.delete(project);

        Project project1 = null;

        Optional<Project> optionalProject = projectRepository.findById(1L);

        if(optionalProject.isPresent()){
            project1 = optionalProject.get();
        }

        Assertions.assertThat(project1).isNull();

    }
}
