package com.knits.jpa.orm.d01.one.to.one.demo02;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

// One to one with shared primary key
// TODO: The issue is that when we use shared primary key
//  one table wont have the id column which reflect on other test runners
// Fixed by using JoinColumn annotation

@DataJpaTest
@EntityScan("com.knits.jpa.orm.d01.one.to.one.demo02") //otherwise finds all other entities in subpackages
@EnableJpaRepositories("com.knits.jpa.orm.d01.one.to.one.demo02") //otherwise doesnt create jpa repositories instances
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_orm"
})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestJpaDemo02 {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository userRepository;

    @Autowired
    private OfficeRepository officeRepository;

    // added for simplicity
    private Map<String, Object> createFakeEmployeeAndOffice() {
        Faker faker = new Faker();

        Employee user = new Employee();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());

        Office office = new Office();
        office.setCity(faker.address().city());
        office.setStreet(faker.address().streetAddress());

        //connect them
        user.setOffice(office);

        return new HashMap<>() {{
            put("user", userRepository.save(user));
            put("office", officeRepository.save(office));
        }};
    }

    private Map<String, Object> createFakeEmployeeAndOffice(
            String firstName,
            String lastName,
            String city,
            String street
    ) {
        Employee user = new Employee();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        Office office = new Office();
        office.setCity(city);
        office.setStreet(street);

        //connect them
        user.setOffice(office);

        return new HashMap<>() {{
            put("user", userRepository.save(user));
            put("office", officeRepository.save(office));
        }};
    }

    @Test
    @Rollback(value = false)
    public void createEmployeeOffice() {
        createFakeEmployeeAndOffice(
                "Stefano",
                "Fiorenza",
                "A Mock City",
                "A Mock Street");
    }

    @Test
    @Rollback(value = false)
    public void updateUserByOffice() {
        Faker faker = new Faker();

        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved office
        Office office = (Office) created.get("office");

        // find the office that belongs to him
        Employee user = userRepository.getEmployeeByOffice(office);
//        System.out.println(user.getFirstName());

        // update it
        user.setFirstName(faker.name().firstName());
        userRepository.save(user);
    }

    @Test
    @Rollback(value = false)
    public void deleteUserByOffice() {
        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved office
        Office office = (Office) created.get("office");

        // find the office that belongs to him
        Employee user = userRepository.getEmployeeByOffice(office);
//        System.out.println(user.getId());

        // delete it
        userRepository.delete(user);
    }

    @Test
    @Rollback(value = false)
    public void findUserByOffice() {
        Map<String, Object> created = createFakeEmployeeAndOffice();

        // get saved user
        Office office = (Office) created.get("office");

        // find the office that belongs to him
        Employee user = userRepository.getEmployeeByOffice(office);
//        System.out.println(user.toString());
    }
}
