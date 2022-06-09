package com.knits.jpa.orm.d03.many.to.many.demo03;

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

// many to many with extra entity

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d03.many.to.many.demo03")
@EnableJpaRepositories("com.knits.jpa.orm.d03.many.to.many.demo03")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_orm_3"
})
@Slf4j
public class TestJpaManyToManyDemo03 {
    @Autowired
    private EmployeeGroupRepository employeeGroupRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    // added for simplicity
    private Map<String, ArrayList> createFakeEmployeeAndGroup() {
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

            employeeGroupRepository.save(group.addEmployee(employee));

            _groups.add(groupRepository.save(group));
            _employees.add(employeeRepository.save(employee));
        }

        _groups.forEach(g -> {
            log.info("Employees for Group: {} ", g.getName());

            g.getEmployees().forEach(eG -> log.info("Employee Found: {} ", eG.getEmployee().getFirstName()));
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
        employeeGroupRepository.save(group.addEmployee(employee));
        employeeGroupRepository.save(group.addEmployee(employee2));

        //emp 3 is in group2
        employeeGroupRepository.save(group2.addEmployee(employee3));

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Group savedGroup = groupRepository.save(group);
        groupRepository.save(group2);

        log.info("Employees for Group: {} ",savedGroup.getName());
        savedGroup.getEmployees().forEach(empGrp -> {
            Employee emp = empGrp.getEmployee();
            log.info("Employee Found: {} ", emp.getFirstName());
        });
    }

    @Test
    @Rollback(value = false)
    public void updateAllEmployeesByGroupTheyBelong() {
        Faker faker = new Faker();
        Map<String, ArrayList> created = createFakeEmployeeAndGroup();

        // get one saved employee
        Group group = (Group) created.get("groups").get(0);

        // update employees
        log.info("Employees before update:");
        group.getEmployees().forEach(eG -> log.info("{}: {}",
                eG.getEmployee().getId().toString(), eG.getEmployee().getFirstName()));

        group.getEmployees().forEach(eG -> {
            eG.getEmployee().setFirstName(faker.name().firstName());
            employeeGroupRepository.save(eG);
        });

        log.info("Employees after update:");
        group.getEmployees().forEach(eG -> log.info("{}: {}",
                eG.getEmployee().getId().toString(), eG.getEmployee().getFirstName()));
    }

    @Test
    @Rollback(value = false)
    public void deleteGroup() {
        Map<String, ArrayList> created = createFakeEmployeeAndGroup();

        // get one saved group
        Group group = (Group) created.get("groups").get(0);
        log.info("Deleting group: {}", group.getName());

        // disconnect
        for (EmployeeGroup employeeGroup: group.getEmployees()) {
            group.removeEmployee(employeeGroup.getEmployee());
        }

        // delete
        groupRepository.delete(group);
    }

    @Test
    @Rollback(value = false)
    public void findAllEmployeesByGroup() {
        Map<String, ArrayList> created = createFakeEmployeeAndGroup();

        // get one saved group
        Group group = (Group) created.get("groups").get(1);

        // find
        Set<EmployeeGroup> employeeGroups = group.getEmployees();
        employeeGroups.forEach(eG -> log.info("Employee name {} and group {}",
                eG.getEmployee().getFirstName(), eG.getGroup().getName()));
    }
}
