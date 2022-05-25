package com.knits.jpa.orm.d03.many.to.many.demo01;

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

        Group group = new Group();
        group.setName("A Mock Group Name");

        employeeRepository.save(employee);
        groupRepository.save(group);


    }
}
