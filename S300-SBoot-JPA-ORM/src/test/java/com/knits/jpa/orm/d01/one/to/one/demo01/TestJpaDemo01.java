package com.knits.jpa.orm.d01.one.to.one.demo01;

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
@ComponentScan("com.knits.jpa.orm.d01.one.to.one.demo01")
@EntityScan("com.knits.jpa.orm.d01.one.to.one.demo01")
@EnableJpaRepositories("com.knits.jpa.orm.d01.one.to.one.demo01")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-01",
        "spring.datasource.username=knits-orm",
        "spring.datasource.password=jpa-tutorial"
})
public class TestJpaDemo01 {

    @Autowired
    private EmployeeRepository userRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Test
    @Rollback(value = false)
    public void initDatabase(){

        Employee user = new Employee();
        user.setFirstName("Stefano");
        user.setLastName("Fiorenza");

        Office office = new Office();
        office.setCity("A Mock City");
        office.setStreet("A Mock Street");

        userRepository.save(user);
        officeRepository.save(office);


    }
}
