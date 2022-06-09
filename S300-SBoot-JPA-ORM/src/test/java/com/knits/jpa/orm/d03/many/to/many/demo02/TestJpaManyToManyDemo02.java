package com.knits.jpa.orm.d03.many.to.many.demo02;

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

import java.util.*;

// many to many bidirectional

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo02")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo02")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_orm_3"
})
@Slf4j
public class TestJpaManyToManyDemo02 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    // added for simplicity
    private Map<String, ArrayList> createFakeEmployeeAndOffice() {
        Faker faker = new Faker();

        // mock employees
        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employee employee = new Employee();
            employee.setFirstName(faker.name().firstName());
            employee.setLastName(faker.name().lastName());

            employees.add(employeeRepository.save(employee));
        }

        // mock groups
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Group group = new Group();
            group.setName(faker.team().name());

            groups.add(groupRepository.save(group));
        }

        // connect them
        ArrayList<Group> _groups = new ArrayList<>();
        ArrayList<Employee> _employees = new ArrayList<>();

        Random random = new Random();
        for (Group group: groups) {
            int number = random.nextInt(groups.size());
            Employee employee = employees.get(number);

            group.getEmployees().add(employee);
            employee.getGroups().add(group);

            _groups.add(groupRepository.save(group));
            _employees.add(employeeRepository.save(employee));
        }

        _groups.forEach(g -> {
            log.info("Employees for Group: {} ", g.getName());

            g.getEmployees().forEach(e -> log.info("Employee Found: {} ", e.getFirstName()));
        });

        return new HashMap<>() {{
            put("groups", _groups);
            put("employees", _employees);
        }};
    }

    @Test
    @Rollback(value = false)
    public void initDatabase() {
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

        Group savedGroup = groupRepository.save(group);
        groupRepository.save(group2);

        log.info("Employees for Group: {} ",savedGroup.getName());
        savedGroup.getEmployees().forEach(emp -> log.info("Employee Found: {} ",emp.getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void updateAllGroupsByBelongingEmployees() {
        Faker faker = new Faker();
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved employee
        Employee employee = (Employee) created.get("employees").get(0);

        // update groups
        log.info("Groups before update:");
        employee.getGroups().forEach(group -> log.info("{}: {}", group.getId().toString(), group.getName()));

        employee.getGroups().forEach(group -> {
            group.setName(faker.team().name());
            groupRepository.save(group);
        });

        log.info("Groups after update:");
        employee.getGroups().forEach(group -> log.info("{}: {}", group.getId().toString(), group.getName()));
    }

    @Test
    @Rollback(value = false)
    public void deleteAllGroupsByEmployee() {
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved employee
        Employee employee = (Employee) created.get("employees").get(0);

        Set<Group> groupsToRemove = new HashSet<>();
        employee.getGroups().forEach(group -> {
            log.info("Group name to be removed: {}, id: {}", group.getName(), group.getId());

            groupsToRemove.add(group);
        });
        employee.getGroups().removeAll(groupsToRemove);

        groupRepository.deleteAll(groupsToRemove);
        employeeRepository.save(employee);
    }

    @Test
    @Rollback(value = false)
    public void findAllGroupsByEmployee() {
        Map<String, ArrayList> created = createFakeEmployeeAndOffice();

        // get one saved group
        Employee employee = (Employee) created.get("employees").get(1);

        // find
        Set<Group> groups = employee.getGroups();
        groups.forEach(group -> log.info("Group name in what {} belongs to: {}",
                employee.getFirstName(), group.getName()));
    }
}
