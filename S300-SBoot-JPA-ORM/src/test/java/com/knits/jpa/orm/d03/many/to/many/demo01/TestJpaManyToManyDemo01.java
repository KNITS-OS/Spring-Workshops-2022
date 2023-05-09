package com.knits.jpa.orm.d03.many.to.many.demo01;

import com.github.javafaker.Faker;
import com.knits.jpa.orm.d02.one.to.many.demo01.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

// many to many link table

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo01")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo01")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_orm_3"
})
@Slf4j
public class TestJpaManyToManyDemo01 {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    // added for simplicity
    private Map<String, ArrayList> createFakeEmployeeAndOffice() {
        Faker faker = new Faker();

        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employee employee = new Employee();
            employee.setFirstName(faker.name().firstName());
            employee.setLastName(faker.name().lastName());

            employees.add(employeeRepository.save(employee));
        }

        ArrayList<Group> groups = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            Group group = new Group();
            group.setName(faker.team().name());

            int number = random.nextInt(employees.size());
            for (int j = 0; j < number; j++) {
                group.getEmployees().add(employees.get(random.nextInt(employees.size())));
            }

            groups.add(groupRepository.save(group));
        }

        groups.forEach(g -> {
            log.info("Employees for Group: {} ", g.getName());

            g.getEmployees().forEach(e -> log.info("Employee Found: {} ", e.getFirstName()));
        });

        return new HashMap<>() {{
            put("groups", groups);
            put("employees", employees);
        }};
    }

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
        group.getEmployees().add(employee);
        group.getEmployees().add(employee2);

        //emp 3 is in group2
        group2.getEmployees().add(employee3);

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Group savedGroup = groupRepository.save(group);
        groupRepository.save(group2);

        log.info("Employees for Group: {} ",savedGroup.getName());
        savedGroup.getEmployees().forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void updateAllEmployeesByGroupTheyBelong() {
        Faker faker = new Faker();
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved group
        Group group = (Group) created.get("groups").get(0);

        // update employees
        log.info("Employees before update:");
        group.getEmployees().forEach(employee -> log.info("{}: {}", employee.getId().toString(), employee.getFirstName()));

        group.getEmployees().forEach(employee -> {
            employee.setFirstName(faker.name().firstName());
            employeeRepository.save(employee);
        });

        log.info("Employees after update:");
        group.getEmployees().forEach(employee -> log.info("{}: {}", employee.getId().toString(), employee.getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void deleteAllEmployeesByGroup() {
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved group
        Group group = (Group) created.get("groups").get(0);

        Set<Employee> employeesToRemove = new HashSet<>();
        group.getEmployees().forEach(employee -> {
            log.info("Employee name to be removed: {}, id: {}", employee.getFirstName(), employee.getId());

            employeesToRemove.add(employee);
        });
        group.getEmployees().removeAll(employeesToRemove);

        if (group.getEmployees().size() == 0) {
            groupRepository.delete(group);
            employeeRepository.deleteAll(employeesToRemove);
            return;
        }
        groupRepository.save(group);
        employeeRepository.deleteAll(employeesToRemove);
    }

    @Test
    @Rollback(value = false)
    public void findAllEmployeesByGroup() {
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved group
        Group group = (Group) created.get("groups").get(1);

        // find
        Set<Employee> employees = group.getEmployees();
        employees.forEach(employee -> log.info("Employee name: {}", employee.getFirstName()));
    }
}
