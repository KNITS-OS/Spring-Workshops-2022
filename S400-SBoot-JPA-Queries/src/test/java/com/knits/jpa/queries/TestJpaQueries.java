package com.knits.jpa.queries;

import static org.assertj.core.api.Assertions.assertThat;
import com.knits.jpa.queries.model.Country;
import com.knits.jpa.queries.model.Employee;
import com.knits.jpa.queries.model.Office;
import com.knits.jpa.queries.repository.CountryRepository;
import com.knits.jpa.queries.repository.EmployeeRepository;
import com.knits.jpa.queries.repository.OfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@EntityScan("com.knits.jpa.queries.model") //otherwise finds all other entities in subpackages
@EnableJpaRepositories("com.knits.jpa.queries.repository") //otherwise doesnt create jpa repositories instances
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-04"
})
@Slf4j
public class TestJpaQueries {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private CountryRepository countryRepository;

    //@Test
    @Rollback(value = false)
    public void initDatabase(){

        Country countryEE = saveNewCountry("Estonia","EE");
        Country countryIT = saveNewCountry("Italy","IT");
        Country countryFR =saveNewCountry("France","FR");
        Country countryDE =saveNewCountry("Germany","DE");

        Office officeInEE =saveNewOffice(countryEE, "Tallinn");
        Office officeInIT =saveNewOffice(countryIT,"Napoli");
        Office officeInFR =saveNewOffice(countryFR,"Paris");
        Office officeInDE =saveNewOffice(countryDE,"Hamburg");

        Employee employee = new Employee();
        employee.setFirstName("Stefano");
        employee.setLastName("Fiorenza");
        employee.setBirthDate(LocalDate.of(1980,11,17));
        employee.setEmail("stefano.fiorenza@email.it");
        employee.setCompanyPhone("243827492362");
        employee.setInternationalName("Stefano Fiorenza");
        employee.setStartDate(LocalDate.of(2010,12,12));
        employee.setEndDate(null);
        employee.setOffice(officeInEE);
        employeeRepository.save(employee);

    }

    @Test
    @Rollback(value = false)
    //@Sql("/data/employees.sql")
    public void testWithSqlInput(){


       // connectEmployeesWithOffices();


        /* Derived queries, JPQL */


        //1) Basics

        /*
        Long employeeCount =employeeRepository.count();
        assertThat(employeeCount).isGreaterThan(0L);


        List<Employee> employees = employeeRepository.getByFirstName("Joya");
        verifyResultsetAndLogFirst(employees);
        */

        List<Employee> employees = employeeRepository.findByFirstName("Ceil");
        verifyResultsetAndLogFirst(employees);


       //2) Nested
        /*
       List<Employee> employees = employeeRepository.findByOffice_Country_Iso2("EE");
       log.info("found {}",employees.size());
       verifyResultsetAndLogFirst(employees);
        */


        /* Custom queries, JPQL */

        //1) With positional parameter
        /*
        List<Employee> employees = employeeRepository.findEmployeesByFirstName("Joya");
        log.info("found {}",employees.size());
        verifyResultsetAndLogFirst(employees);
        */

        //2) With named parameter
        /*

        List<Employee> employees = employeeRepository.findEmployeesByFirstNameWithParameter("Joya");
        log.info("found {}",employees.size());
        verifyResultsetAndLogFirst(employees);
         */

        //3) nested query (join)
        /*
        List<Employee> employees = employeeRepository.findByIso2("EE");
        log.info("found {}",employees.size());
        verifyResultsetAndLogFirst(employees);
        */





    }



    private void connectEmployeesWithOffices(){

        Country countryEE = saveNewCountry("Estonia","EE");
        Country countryIT = saveNewCountry("Italy","IT");
        Country countryFR =saveNewCountry("France","FR");
        Country countryDE =saveNewCountry("Germany","DE");

        Office officeInEE =saveNewOffice(countryEE, "Tallinn");
        Office officeInIT =saveNewOffice(countryIT,"Napoli");
        Office officeInFR =saveNewOffice(countryFR,"Paris");
        Office officeInDE =saveNewOffice(countryDE,"Hamburg");

        List<Country>  countries = List.of(countryEE,countryIT,countryFR,countryDE);
        List<Office>  offices = List.of(officeInEE,officeInIT,officeInFR,officeInDE);

        List<Employee> employees = employeeRepository.findAll();

        int counter=0;

        for (int i=0; i<employees.size(); i++){
            if (counter==offices.size()){
                counter=0;
            }
            employees.get(i).setOffice(offices.get(counter));
            counter++;
        }

        employeeRepository.saveAll(employees);
    }



    private void verifyResultsetAndLogFirst(List<Employee> employees){
        assertThat(employees.size()).isGreaterThan(0);
        log.info(employees.get(0).toString());
    }


    private Country saveNewCountry(String countryName, String iso2){
        Country country = new Country();
        country.setName(countryName);
        country.setIso2(iso2);
        return countryRepository.save(country);
    }

    private Office saveNewOffice(Country country, String city){
        Office office = new Office();
        office.setCity(city);
        office.setStreet("A Mock Street");
        office.setCountry(country);
        return officeRepository.save(office);
    }

}
